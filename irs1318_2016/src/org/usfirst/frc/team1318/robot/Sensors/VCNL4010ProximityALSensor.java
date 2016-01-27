package org.usfirst.frc.team1318.robot.Sensors;

import edu.wpi.first.wpilibj.I2C;

public class VCNL4010ProximityALSensor
{
    private static final int ADDRESS = 0x13;

    private static final int COMMAND_REGISTER = 0x80; // Command register
    private static final int COMMAND_AMBIENT_LIGHT_ON_DEMAND = 0x10; // trigger ambient light on-demand measurement
    private static final int COMMAND_PROXIMITY_ON_DEMAND = 0x08; // trigger proximity on-demand measurement
    private static final int COMMAND_AMBIENT_LIGHT_ENABLE = 0x04; // enable ambient light periodic mode
    private static final int COMMAND_PROXIMITY_ENABLE = 0x02; // enable proximity periodic mode
    private static final int COMMAND_SELF_TIMED_ENABLE = 0x01; // enable self-timed (periodic) mode

    private static final int ID_REGISTER = 0x81; // ID register (read-only)
    private static final int EXPECTED_ID = 0x21; // expected ID value for VCNL4010 light sensor

    private static final int PROXIMITY_RATE_REGISTER = 0x82; // Proximity rate register
    private static final int IR_LED_REGISTER = 0x83; // LED current setting for proximity mode (set it from 0 to 20d. Current = 10 x setting mA)

    private static final int AMBIENT_LIGHT_SETTINGS_REGISTER = 0x84; // Ambient light settings register
    private static final int AMBIENT_LIGHT_CONTINUOUS_MODE = 0x80; // continuous conversion mode (default: off)
    private static final int AMBIENT_LIGHT_AUTO_OFFSET_MODE = 0x08; // automatic offset compensation (default: on)

    private static final int AMBIENT_LIGHT_RESULT_HIGH_REGISTER = 0x85; // high byte for the ambient light measurement
    private static final int AMBIENT_LIGHT_RESULT_LOW_REGISTER = 0x86; // low byte for the ambient light measurement
    private static final int PROXIMITY_RESULT_HIGH_REGISTER = 0x85; // high byte for the proximity measurement
    private static final int PROXIMITY_RESULT_LOW_REGISTER = 0x86; // low byte for the proximity measurement

    public enum ProximityRate
    {
        Rate1_9x(0x0),  // 000 - 1.95 measurements/s (DEFAULT)
        Rate3_9x(0x1),  // 001 - 3.90625 measurements/s
        Rate7_8x(0x2),  // 010 - 7.8125 measurements/s
        Rate16_6x(0x3), // 011 - 16.625 measurements/s
        Rate31_2x(0x4), // 100 - 31.25 measurements/s
        Rate62_5x(0x5), // 101 - 62.5 measurements/s
        Rate125(0x6),   // 110 - 125 measurements/s
        Rate250(0x7);   // 111 - 250 measurements/s

        public final int Value;

        private ProximityRate(int value)
        {
            this.Value = value;
        }
    }

    public enum AmbientLightRate
    {
        Rate1x(0x00),  // 000 - 1 sample/s
        Rate2x(0x10),  // 001 - 2 samples/s (DEFAULT)
        Rate3x(0x20),  // 010 - 3 samples/s
        Rate4x(0x30),  // 011 - 4 samples/s
        Rate5x(0x40),  // 100 - 5 samples/s
        Rate6x(0x50),  // 101 - 6 samples/s
        Rate8x(0x60),  // 110 - 8 samples/s
        Rate10x(0x70); // 111 - 10 samples/s

        public final int Value;

        private AmbientLightRate(int value)
        {
            this.Value = value;
        }
    }

    public enum AmbientLightAveragingFunction
    {
        x1(0x0),   // 000 - Average of 1 conversion
        x2(0x1),   // 001 - Average of 2 conversions
        x4(0x2),   // 010 - Average of 4 conversions
        x8(0x3),   // 011 - Average of 8 conversions
        x16(0x4),  // 100 - Average of 16 conversions
        x32(0x5),  // 101 - Average of 32 conversions (DEFAULT)
        x64(0x6),  // 110 - Average of 64 conversions
        x128(0x7); // 111 - Average of 128 conversions

        public final int Value;

        private AmbientLightAveragingFunction(int value)
        {
            this.Value = value;
        }
    }

    private final I2C connection;

    public VCNL4010ProximityALSensor(I2C.Port port)
    {
        this.connection = new I2C(port, VCNL4010ProximityALSensor.ADDRESS);

        // check id.
        byte[] buffer = new byte[1];
        this.connection.read(ID_REGISTER, 1, buffer);
        int id = Byte.toUnsignedInt(buffer[0]);
        if (id != EXPECTED_ID)
        {
            throw new RuntimeException("Unexpected id: " + id);
        }

        this.start();
    }

    public void start()
    {
        this.connection.write(COMMAND_REGISTER, COMMAND_SELF_TIMED_ENABLE | COMMAND_PROXIMITY_ENABLE | COMMAND_AMBIENT_LIGHT_ENABLE);
    }

    public void stop()
    {
        this.connection.write(COMMAND_REGISTER, 0x0);
    }

    public void setProximityRate(ProximityRate proximityRate)
    {
        this.connection.write(PROXIMITY_RATE_REGISTER, proximityRate.Value);
    }

    /**
     * Set LED current.
     * @param ledCurrent value between 0 and 20, where the current = 10 x ledCurrent mA
     */
    public void setLEDCurrent(int ledCurrent)
    {
        ledCurrent %= 20;
        this.connection.write(PROXIMITY_RATE_REGISTER, ledCurrent);
    }

    public void setAmbientLightSettings(
        boolean continuousConversion,
        AmbientLightRate measurementRate,
        boolean autoOffsetCompensation,
        AmbientLightAveragingFunction averagingFunction)
    {
        int value = 0x0;
        if (continuousConversion)
        {
            value |= AMBIENT_LIGHT_CONTINUOUS_MODE;
        }

        value |= measurementRate.Value;
        if (autoOffsetCompensation)
        {
            value |= AMBIENT_LIGHT_AUTO_OFFSET_MODE;
        }

        value |= averagingFunction.Value;
        this.connection.write(AMBIENT_LIGHT_SETTINGS_REGISTER, value);
    }

    public int getProximityValue()
    {
        byte[] buffer = new byte[2];
        this.connection.read(PROXIMITY_RESULT_HIGH_REGISTER, 2, buffer);
        return IntFromBytePair(buffer);
    }

    public int getAmbientLightValue()
    {
        byte[] buffer = new byte[2];
        this.connection.read(AMBIENT_LIGHT_RESULT_HIGH_REGISTER, 2, buffer);
        return IntFromBytePair(buffer);
    }

    /**
     * VCNL4010 tends to have bytes in high, low order.
     * @param buffer of bytes
     * @return integer between 0 and 65535
     */
    private static int IntFromBytePair(byte[] buffer)
    {
        int highByte = Byte.toUnsignedInt(buffer[0]);
        int lowByte = Byte.toUnsignedInt(buffer[1]);

        return ((highByte << 8) | lowByte);
    }
}
