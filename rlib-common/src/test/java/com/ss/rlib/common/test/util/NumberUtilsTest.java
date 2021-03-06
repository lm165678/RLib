package com.ss.rlib.common.test.util;

import static org.junit.jupiter.api.Assertions.*;
import com.ss.rlib.common.util.NumberUtils;
import org.junit.jupiter.api.Test;

/**
 * Test methods in {@link NumberUtils}
 *
 * @author JavaSaBr
 */
class NumberUtilsTest {

    @Test
    void shouldCheckStringIsLong() {
        assertTrue(NumberUtils.isLong("1"));
        assertTrue(NumberUtils.isLong("123123213123"));
        assertFalse(NumberUtils.isLong("notlong"));
        assertFalse(NumberUtils.isLong(null));
        assertFalse(NumberUtils.isLong("2.1234"));
    }

    @Test
    void shouldSafetyConvertStringToLong() {
        assertNotNull(NumberUtils.safeToLong("1"));
        assertNotNull(NumberUtils.safeToLong("123123213123"));
        assertNull(NumberUtils.safeToLong("notlong"));
        assertNull(NumberUtils.safeToLong(null));
        assertNull(NumberUtils.safeToLong("2.1234"));
    }

    @Test
    void shouldConvertStringToOptionalLong() {
        assertTrue(NumberUtils.toOptionalLong("1").isPresent());
        assertTrue(NumberUtils.toOptionalLong("123123213123").isPresent());
        assertFalse(NumberUtils.toOptionalLong("notlong").isPresent());
        assertFalse(NumberUtils.toOptionalLong(null).isPresent());
        assertFalse(NumberUtils.toOptionalLong("2.1234").isPresent());
    }

    @Test
    void shouldSafetyConvertStringToInt() {
        assertNotNull(NumberUtils.safeToInt("1"));
        assertNotNull(NumberUtils.safeToInt("123124"));
        assertNull(NumberUtils.safeToInt("notlong"));
        assertNull(NumberUtils.safeToInt(null));
        assertNull(NumberUtils.safeToInt("2.1234"));
    }

    @Test
    void shouldReadBitsCorrectly() {

        assertTrue(NumberUtils.isSetBit((byte) 0b00000001, 0));
        assertTrue(NumberUtils.isNotSetBit((byte) 0b00000000, 0));

        assertTrue(NumberUtils.isSetBit((byte) 0b00000010, 1));
        assertTrue(NumberUtils.isNotSetBit((byte) 0b00000000, 1));

        assertTrue(NumberUtils.isSetBit((byte) 0b00000100, 2));
        assertTrue(NumberUtils.isNotSetBit((byte) 0b00000000, 2));

        assertTrue(NumberUtils.isSetBit((byte) 0b00001000, 3));
        assertTrue(NumberUtils.isNotSetBit((byte) 0b00000000, 3));

        assertTrue(NumberUtils.isSetBit((byte) 0b00010000, 4));
        assertTrue(NumberUtils.isNotSetBit((byte) 0b00000000, 4));

        assertTrue(NumberUtils.isSetBit((byte) 0b00100000, 5));
        assertTrue(NumberUtils.isNotSetBit((byte) 0b00000000, 5));

        assertTrue(NumberUtils.isSetBit((byte) 0b01000000, 6));
        assertTrue(NumberUtils.isNotSetBit((byte) 0b00000000, 6));

        assertTrue(NumberUtils.isSetBit((byte) 0b10000000, 7));
        assertTrue(NumberUtils.isNotSetBit((byte) 0b00000000, 7));

        assertTrue(NumberUtils.isSetBit((byte) 0b11111111, 0));
        assertTrue(NumberUtils.isSetBit((byte) 0b11111111, 1));
        assertTrue(NumberUtils.isSetBit((byte) 0b11111111, 2));
        assertTrue(NumberUtils.isSetBit((byte) 0b11111111, 3));
        assertTrue(NumberUtils.isSetBit((byte) 0b11111111, 4));
        assertTrue(NumberUtils.isSetBit((byte) 0b11111111, 5));
        assertTrue(NumberUtils.isSetBit((byte) 0b11111111, 6));
        assertTrue(NumberUtils.isSetBit((byte) 0b11111111, 7));

        assertTrue(NumberUtils.isNotSetBit((byte) 0b00000000, 0));
        assertTrue(NumberUtils.isNotSetBit((byte) 0b00000000, 1));
        assertTrue(NumberUtils.isNotSetBit((byte) 0b00000000, 2));
        assertTrue(NumberUtils.isNotSetBit((byte) 0b00000000, 3));
        assertTrue(NumberUtils.isNotSetBit((byte) 0b00000000, 4));
        assertTrue(NumberUtils.isNotSetBit((byte) 0b00000000, 5));
        assertTrue(NumberUtils.isNotSetBit((byte) 0b00000000, 6));
        assertTrue(NumberUtils.isNotSetBit((byte) 0b00000000, 7));
    }

    @Test
    void shouldWriteHighLowBitsCorrectly() {

        for (int low = 0; low < 16; low++) {
            for (int high = 0; high < 16; high++) {
                var result = NumberUtils.setHighByteBits(low, high);
                assertEquals(high, NumberUtils.getHighByteBits(result));
                assertEquals(low, NumberUtils.getLowByteBits(result));
            }
        }

        for (int low = 0; low < 16; low++) {
            var result = NumberUtils.setLowByteBits(256, low);
            assertEquals(16, NumberUtils.getHighByteBits(result));
            assertEquals(low, NumberUtils.getLowByteBits(result));
        }
    }

    @Test
    void shouldReadHighLowBitsCorrectly() {

        for (int i = 0; i < 16; i++) {
            assertEquals(i, NumberUtils.getHighByteBits(i << 4));
        }

        assertEquals(0b0000_1000, NumberUtils.getHighByteBits(0b1000_0100));
        assertEquals(0b0000_0100, NumberUtils.getHighByteBits(0b0100_1000));
        assertEquals(0b0000_0010, NumberUtils.getHighByteBits(0b0010_0010));
        assertEquals(0b0000_0001, NumberUtils.getHighByteBits(0b0001_0001));
        assertEquals(0b0000_0101, NumberUtils.getHighByteBits(0b0101_1000));

        for (int i = 0; i < 16; i++) {
            assertEquals(i, NumberUtils.getLowByteBits(i & 0x0F));
        }

        assertEquals(0b0000_1000, NumberUtils.getLowByteBits(0b1000_1000));
        assertEquals(0b0000_0100, NumberUtils.getLowByteBits(0b0100_0100));
        assertEquals(0b0000_0010, NumberUtils.getLowByteBits(0b0010_0010));
        assertEquals(0b0000_0001, NumberUtils.getLowByteBits(0b0001_0001));
        assertEquals(0b0000_0101, NumberUtils.getLowByteBits(0b0101_0101));
    }

    @Test
    void shouldChangeBitsInByteCorrectly() {

        assertTrue(NumberUtils.isSetBit(NumberUtils.setBit(0, 0), 0));
        assertTrue(NumberUtils.isSetBit(NumberUtils.setBit(0, 1), 1));
        assertTrue(NumberUtils.isSetBit(NumberUtils.setBit(0, 2), 2));
        assertTrue(NumberUtils.isSetBit(NumberUtils.setBit(0, 3), 3));
        assertTrue(NumberUtils.isSetBit(NumberUtils.setBit(0, 4), 4));
        assertTrue(NumberUtils.isSetBit(NumberUtils.setBit(0, 5), 5));
        assertTrue(NumberUtils.isSetBit(NumberUtils.setBit(0, 6), 6));
        assertTrue(NumberUtils.isSetBit(NumberUtils.setBit(0, 7), 7));

        assertTrue(NumberUtils.isNotSetBit(NumberUtils.unsetBit(255, 0), 0));
        assertTrue(NumberUtils.isNotSetBit(NumberUtils.unsetBit(255, 1), 1));
        assertTrue(NumberUtils.isNotSetBit(NumberUtils.unsetBit(255, 2), 2));
        assertTrue(NumberUtils.isNotSetBit(NumberUtils.unsetBit(255, 3), 3));
        assertTrue(NumberUtils.isNotSetBit(NumberUtils.unsetBit(255, 4), 4));
        assertTrue(NumberUtils.isNotSetBit(NumberUtils.unsetBit(255, 5), 5));
        assertTrue(NumberUtils.isNotSetBit(NumberUtils.unsetBit(255, 6), 6));
        assertTrue(NumberUtils.isNotSetBit(NumberUtils.unsetBit(255, 7), 7));
    }

    @Test
    void shouldValidateIntegerCorrectly() {

        // int
        assertEquals(10, NumberUtils.validate(10, 1, 20));
        assertEquals(-20, NumberUtils.validate(-20, -21, 10));
        assertEquals(-1000, NumberUtils.validate(-1000, -1050, -1000));

        assertEquals(10, NumberUtils.validate(10, 1, 20, IllegalArgumentException::new));
        assertEquals(-20, NumberUtils.validate(-20, -21, 10, IllegalArgumentException::new));
        assertEquals(-1000, NumberUtils.validate(-1000, -1050, -1000, IllegalArgumentException::new));

        assertThrows(IllegalArgumentException.class, () ->
            NumberUtils.validate(10, 20, 50, IllegalArgumentException::new));
        assertThrows(IllegalArgumentException.class, () ->
            NumberUtils.validate(-50, -70, -51, IllegalArgumentException::new));

        assertThrows(IllegalArgumentException.class, () ->
            NumberUtils.validate(10, 20, 50));
        assertThrows(IllegalArgumentException.class, () ->
            NumberUtils.validate(-50, -70, -51));

        // long
        assertEquals(10, NumberUtils.validate(10L, 1, 20));
        assertEquals(-20, NumberUtils.validate(-20L, -21, 10));
        assertEquals(-1000, NumberUtils.validate(-1000L, -1050, -1000));

        assertEquals(10, NumberUtils.validate(10L, 1, 20, IllegalArgumentException::new));
        assertEquals(-20, NumberUtils.validate(-20L, -21, 10, IllegalArgumentException::new));
        assertEquals(-1000, NumberUtils.validate(-1000L, -1050, -1000, IllegalArgumentException::new));

        assertThrows(IllegalArgumentException.class, () ->
            NumberUtils.validate(10L, 20, 50, IllegalArgumentException::new));
        assertThrows(IllegalArgumentException.class, () ->
            NumberUtils.validate(-50L, -70, -51, IllegalArgumentException::new));

        assertThrows(IllegalArgumentException.class, () -> NumberUtils.validate(10L, 20, 50));
        assertThrows(IllegalArgumentException.class, () -> NumberUtils.validate(-50L, -70, -51));
    }

    @Test
    void shouldValidateBooleanCorrectly() {

        assertTrue(NumberUtils.toBoolean(1));
        assertFalse(NumberUtils.toBoolean(0));
        assertTrue(NumberUtils.toBoolean(1L));
        assertFalse(NumberUtils.toBoolean(0L));
        assertTrue(NumberUtils.toBoolean(1, IllegalArgumentException::new));
        assertFalse(NumberUtils.toBoolean(0, IllegalArgumentException::new));
        assertTrue(NumberUtils.toBoolean(1L, IllegalArgumentException::new));
        assertFalse(NumberUtils.toBoolean(0L, IllegalArgumentException::new));

        assertThrows(IllegalArgumentException.class, () -> NumberUtils.toBoolean(2));
        assertThrows(IllegalArgumentException.class, () -> NumberUtils.toBoolean(-1));
        assertThrows(IllegalArgumentException.class, () -> NumberUtils.toBoolean(2L));
        assertThrows(IllegalArgumentException.class, () -> NumberUtils.toBoolean(-1L));
    }

    @Test
    void shouldEqualsNumbersCorrectly() {

        assertTrue(NumberUtils.equals((byte) 10, (byte) 10));
        assertTrue(NumberUtils.equals((short) 10, (short) 10));
        assertTrue(NumberUtils.equals(10, 10));
        assertTrue(NumberUtils.equals(10L, 10L));
        assertTrue(NumberUtils.equals(10F, 10F));
        assertTrue(NumberUtils.equals(10D, 10D));

        assertFalse(NumberUtils.equals((byte) -10, (byte) 10));
        assertFalse(NumberUtils.equals((short) -10, (short) 10));
        assertFalse(NumberUtils.equals(-10, 10));
        assertFalse(NumberUtils.equals(-10L, 10L));
        assertFalse(NumberUtils.equals(-10F, 10F));
        assertFalse(NumberUtils.equals(-10D, 10D));
    }
}
