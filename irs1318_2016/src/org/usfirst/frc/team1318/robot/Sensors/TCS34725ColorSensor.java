package org.usfirst.frc.team1318.robot.Sensors;

import edu.wpi.first.wpilibj.I2C;

public class TCS34725ColorSensor
{
    private static final int ADDRESS = 0x29;
    private static final int COMMAND_BIT = 0x80;

    private static final int ENABLE_REGISTER = 0x00; // enable - used to enable RGBC interrupt, Wait, RGBC, and Power
    private static final int ENABLE_AIEN = 0x10; // enable RGBC Interrupt (1 enables, 0 disables)
    private static final int ENABLE_WEN = 0x08; // enable Wait (1 enables, 0 disables)
    private static final int ENABLE_AEN = 0x02; // enable RGBC (1 enables, 0 disables)
    private static final int ENABLE_PON = 0x01; // Power on (1 enables, 0 disables)

    private static final int ATIME_REGISTER = 0x01; // RGBC integration time (use IntegrationTime for data)
    private static final int WTIME_REGISTER = 0x03; // Wait time (use WaitTime for data, consider WLONG/Config as well)
    private static final int CONFIG_REGISTER = 0x0D; // Configuration - used only to configure WLONG
    private static final int CONFIG_NONE = 0x00; // disable WLONG
    private static final int CONFIG_WLONG = 0x02; // enable WLONG
    private static final int CONTROL_REGISTER = 0x0F; // Control gain (use Gain for data)
    private static final int ID_REGISTER = 0x12; // ID register (read-only)
    private static final int EXPECTED_ID = 0x44; // expected ID value for TCS34725 light sensor
    private static final int CLEAR_DATA_LOW_REGISTER = 0x14; // clear color sensor data - low byte
    //private static final int CLEAR_DATA_HIGH_REGISTER = 0x15; // clear color sensor data - high byte
    private static final int RED_DATA_LOW_REGISTER = 0x16; // red color sensor data - low byte
    //private static final int RED_DATA_HIGH_REGISTER = 0x17; // red color sensor data - high byte
    private static final int GREEN_DATA_LOW_REGISTER = 0x18; // green color sensor data - low byte
    //private static final int GREEN_DATA_HIGH_REGISTER = 0x19; // green color sensor data - high byte
    private static final int BLUE_DATA_LOW_REGISTER = 0x1A; // blue color sensor data - low byte
    //private static final int BLUE_DATA_HIGH_REGISTER = 0x1B; // blue color sensor data - high byte

    public enum IntegrationTime
    {
        Time2_4MS(0xFF), // 2.4 ms - 1 cycle,    max count = 1024
        Time24MS(0xF6),  // 24 ms  - 10 cycles,  max count = 10240
        Time50MS(0xEB),  // 50 ms  - 20 cycles,  max count = 20480
        Time101MS(0xD5), // 101 ms - 42 cycles,  max count = 43008
        Time154MS(0xC0), // 154 ms - 64 cycles,  max count = 65535
        Time700MS(0x00); // 700 ms - 256 cycles, max count = 65535

        public final int Value;

        private IntegrationTime(int value)
        {
            this.Value = value;
        }
    }

    public enum Gain
    {
        X1(0x00),  // no gain
        X4(0x01),  // 4x gain
        X16(0x02), // 16x gain
        X60(0x03); // 60x gain

        public final int Value;

        private Gain(int value)
        {
            this.Value = value;
        }
    }

    public enum WaitTime
    {
        WaitNONE(false), // no wait...
        Wait2_4MS(false, 0xFF), // 2.4*1 =  2.4 ms
        Wait20MS(false, 0xF8), // 2.4*8 = 19.2 ms
        Wait614MS(false, 0x00), // 2.4*256 = 614.4 ms
        Wait1Sec(true, 0xDD), // 2.4*35*12 = 1.008 sec
        Wait2Sec(true, 0xBA); // 2.4*70*12 = 2.016 sec

        public final boolean Enabled;
        public final boolean Wlong;
        public final int InverseMultiplier;

        private WaitTime(boolean enabled)
        {
            this.Enabled = false;
            this.Wlong = false;
            this.InverseMultiplier = 0;
        }

        private WaitTime(boolean wlong, int inverseMultiplier)
        {
            this.Enabled = true;
            this.Wlong = wlong;
            this.InverseMultiplier = inverseMultiplier;
        }
    }

    public class Color
    {
        private final int red;
        private final int green;
        private final int blue;
        private final int clear;

        public Color(int red, int green, int blue, int clear)
        {
            this.red = red;
            this.green = green;
            this.blue = blue;
            this.clear = clear;
        }

        public int getRed()
        {
            return this.red;
        }

        public int getGreen()
        {
            return this.green;
        }

        public int getBlue()
        {
            return this.blue;
        }

        public int getClear()
        {
            return this.clear;
        }
    }

    private final I2C connection;
    private IntegrationTime integration;
    private Gain gain;
    private WaitTime wait;
    private boolean wlongEnabled;
    private boolean waitEnabled;
    private boolean rgbcEnabled;
    private boolean powerEnabled;
    private boolean interruptEnabled;

    public TCS34725ColorSensor(I2C.Port port)
    {
        this(port, IntegrationTime.Time2_4MS, Gain.X1);
    }

    public TCS34725ColorSensor(I2C.Port port, IntegrationTime integration)
    {
        this(port, integration, Gain.X1);
    }

    public TCS34725ColorSensor(I2C.Port port, Gain gain)
    {
        this(port, IntegrationTime.Time2_4MS, gain);
    }

    public TCS34725ColorSensor(I2C.Port port, IntegrationTime integration, Gain gain)
    {
        this.connection = new I2C(port, TCS34725ColorSensor.ADDRESS);
        this.wlongEnabled = false;
        this.waitEnabled = false;
        this.rgbcEnabled = false;
        this.powerEnabled = false;
        this.interruptEnabled = false;

        // check id.
        byte[] buffer = new byte[1];
        this.connection.read(COMMAND_BIT | ID_REGISTER, 1, buffer);
        int id = Byte.toUnsignedInt(buffer[0]);
        if (id != EXPECTED_ID)
        {
            throw new RuntimeException("Unexpected id: " + id);
        }

        // set integration time and gain
        this.setIntegration(integration);
        this.setGain(gain);

        // start by default
        this.start();
    }

    public void start()
    {
        this.connection.write(COMMAND_BIT | ENABLE_REGISTER, ENABLE_PON);

        try
        {
            Thread.sleep(3);
        }
        catch (InterruptedException e)
        {
        }

        int value = this.calculateEnabledValue(true, true, this.waitEnabled, this.interruptEnabled);

        this.connection.write(COMMAND_BIT | ENABLE_REGISTER, value);

        byte[] buffer = new byte[1];
        this.connection.read(COMMAND_BIT | ENABLE_REGISTER, 1, buffer);

        this.powerEnabled = true;
        this.rgbcEnabled = true;
    }

    public void stop()
    {
        // disable just PON and AEN - don't disable anything else we may have enabled...
        int value = this.calculateEnabledValue(false, false, this.waitEnabled, this.interruptEnabled);

        this.connection.write(COMMAND_BIT | ENABLE_REGISTER, value);

        this.powerEnabled = false;
        this.rgbcEnabled = false;
    }

    public Color readColor()
    {
        byte[] buffer = new byte[2];
        this.connection.read(COMMAND_BIT | RED_DATA_LOW_REGISTER, 2, buffer);
        int red = IntFromBytePair(buffer);

        this.connection.read(COMMAND_BIT | GREEN_DATA_LOW_REGISTER, 2, buffer);
        int green = IntFromBytePair(buffer);

        this.connection.read(COMMAND_BIT | BLUE_DATA_LOW_REGISTER, 2, buffer);
        int blue = IntFromBytePair(buffer);

        this.connection.read(COMMAND_BIT | CLEAR_DATA_LOW_REGISTER, 2, buffer);
        int clear = IntFromBytePair(buffer);

        return new Color(red, green, blue, clear);
    }

    public void setIntegration(IntegrationTime integration)
    {
        if (this.integration == integration)
        {
            return;
        }

        this.connection.write(COMMAND_BIT | ATIME_REGISTER, integration.Value);
        this.integration = integration;
    }

    public void setGain(Gain gain)
    {
        if (this.gain == gain)
        {
            return;
        }

        this.connection.write(COMMAND_BIT | CONTROL_REGISTER, gain.Value);
        this.gain = gain;
    }

    public void setWait(WaitTime wait)
    {
        if (this.wait == wait)
        {
            return;
        }

        // disable wait:
        if (this.waitEnabled && !wait.Enabled)
        {
            int value = this.calculateEnabledValue(this.powerEnabled, this.rgbcEnabled, false, this.interruptEnabled);
            this.connection.write(COMMAND_BIT | ENABLE_REGISTER, value);
            return;
        }

        // enable wait:
        if (!this.waitEnabled && wait.Enabled)
        {
            int value = this.calculateEnabledValue(this.powerEnabled, this.rgbcEnabled, true, this.interruptEnabled);
            this.connection.write(COMMAND_BIT | ENABLE_REGISTER, value);
            this.waitEnabled = true;
        }

        // fix WLONG:
        if (!this.wlongEnabled && wait.Wlong)
        {
            this.connection.write(CONFIG_REGISTER, CONFIG_WLONG);
        }
        else if (this.wlongEnabled && !wait.Wlong)
        {
            this.connection.write(CONFIG_REGISTER, CONFIG_NONE);
        }

        // update wait time:
        this.connection.write(COMMAND_BIT | WTIME_REGISTER, wait.InverseMultiplier);
    }

    private int calculateEnabledValue(boolean power, boolean rgbc, boolean wait, boolean interrupt)
    {
        int value = 0x0;
        if (power)
        {
            value |= ENABLE_PON;
        }

        if (rgbc)
        {
            value |= ENABLE_AEN;
        }

        if (wait)
        {
            value |= ENABLE_WEN;
        }

        if (interrupt)
        {
            value |= ENABLE_AIEN;
        }

        return value;
    }

    /**
     * TCS34725 tends to have bytes in low, high order.
     * @param buffer of bytes
     * @return integer between 0 and 65535
     */
    private static int IntFromBytePair(byte[] buffer)
    {
        int lowByte = Byte.toUnsignedInt(buffer[0]);
        int highByte = Byte.toUnsignedInt(buffer[1]);

        return ((highByte << 8) | lowByte);
    }
}
