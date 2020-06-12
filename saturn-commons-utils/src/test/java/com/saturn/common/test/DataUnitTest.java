package com.saturn.common.test;

import com.saturn.commons.utils.DataUnit;
import org.junit.Assert;
import org.junit.Test;


/**
 * DataUnit conversion test
 */
public class DataUnitTest {

    @Test
    public void BitTest() {
        DataUnit unit= DataUnit.BIT;
        Assert.assertEquals("Invalid Bit to Bit conversion",10f, unit.toBits(10),0);
        Assert.assertEquals("Invalid Bit to Nibble conversion",4f, unit.toNibbles(16),0);
        Assert.assertEquals("Invalid Bit to Byte conversion",2f, unit.toBytes(16),0);
        Assert.assertEquals("Invalid Bit to Kibibyte conversion",2f, unit.toKibibytes(16384),0);
        Assert.assertEquals("Invalid Bit to Mibibyte conversion",2f, unit.toMebibytes(16777216),0);
        Assert.assertEquals("Invalid Bit to Gibibyte conversion",2f, unit.toGibibytes(17179869184f),0);
        Assert.assertEquals("Invalid Bit to Tebibyte conversion",2f, unit.toTebibytes(1.759218604E13f),0);
        Assert.assertEquals("Invalid Bit to Pebibyte conversion",2f, unit.toPebibytes(1.801439851E16f),0);
        Assert.assertEquals("Invalid Bit to Exbibyte conversion",2f, unit.toExbibytes(1.844674407E19f),0);
        Assert.assertEquals("Invalid Bit to Zebibyte conversion",2f, unit.toZebibytes(1.888946593E22f),0);
        Assert.assertEquals("Invalid Bit to Yobibyte conversion",2f, unit.toYobibytes(1.934281311E25f),0);
    }


    @Test
    public void ByteTest() {
        DataUnit unit= DataUnit.BYTE;
        Assert.assertEquals("Invalid Byte to Bit conversion",40f, unit.toBits(5),0);
        Assert.assertEquals("Invalid Byte to Nibble conversion",20f, unit.toNibbles(10),0);
        Assert.assertEquals("Invalid Byte to Byte conversion",5f, unit.toBytes(5),0);
        Assert.assertEquals("Invalid Byte to Kibibyte conversion",2f, unit.toKibibytes(2048),0);
        Assert.assertEquals("Invalid Byte to Mibibyte conversion",2f, unit.toMebibytes(2097152),0);
        Assert.assertEquals("Invalid Byte to Gibibyte conversion",2f, unit.toGibibytes(2147483648f),0);
        Assert.assertEquals("Invalid Byte to Tebibyte conversion",2f, unit.toTebibytes(2.199023256E12f),0);
        Assert.assertEquals("Invalid Byte to Pebibyte conversion",2f, unit.toPebibytes(2.251799814E15f),0);
        Assert.assertEquals("Invalid Byte to Exbibyte conversion",2f, unit.toExbibytes(2.305843009E18f),0);
        Assert.assertEquals("Invalid Byte to Zebibyte conversion",2f, unit.toZebibytes(2.361183241E21f),0);
        Assert.assertEquals("Invalid Byte to Yobibyte conversion",2f, unit.toYobibytes(2.417851639E24f),0);
    }


    @Test
    public void KibibyteTest() {
        DataUnit unit= DataUnit.KIBIBYTE;
        Assert.assertEquals("Invalid Kibibyte to Bit conversion",40960f, unit.toBits(5),0);
        Assert.assertEquals("Invalid Kibibyte to Nibble conversion",10240f, unit.toNibbles(5),0);
        Assert.assertEquals("Invalid Kibibyte to Byte conversion",5120f, unit.toBytes(5),0);
        Assert.assertEquals("Invalid Kibibyte to Kibibyte conversion",10f, unit.toKibibytes(10),0);
        Assert.assertEquals("Invalid Kibibyte to Mibibyte conversion",2f, unit.toMebibytes(2048),0);
        Assert.assertEquals("Invalid Kibibyte to Gibibyte conversion",2f, unit.toGibibytes(2097152),0);
        Assert.assertEquals("Invalid Kibibyte to Tebibyte conversion",2f, unit.toTebibytes(2147483648f),0);
        Assert.assertEquals("Invalid Kibibyte to Pebibyte conversion",2f, unit.toPebibytes(2.199023256E12f),0);
        Assert.assertEquals("Invalid Kibibyte to Exbibyte conversion",2f, unit.toExbibytes(2.251799814E15f),0);
        Assert.assertEquals("Invalid Kibibyte to Zebibyte conversion",2f, unit.toZebibytes(2.305843009E18f),0);
        Assert.assertEquals("Invalid Kibibyte to Yobibyte conversion",2f, unit.toYobibytes(2.361183241E21f),0);
    }


    @Test
    public void MebibyteTest() {
        DataUnit unit= DataUnit.MEBIBYTE;
        Assert.assertEquals("Invalid Mebibyte to Bit conversion",41943040f, unit.toBits(5),0);
        Assert.assertEquals("Invalid Mebibyte to Nibble conversion",10485760f, unit.toNibbles(5),0);
        Assert.assertEquals("Invalid Mebibyte to Byte conversion",5242880f, unit.toBytes(5),0);
        Assert.assertEquals("Invalid Mebibyte to Kibibyte conversion",5120f, unit.toKibibytes(5),0);
        Assert.assertEquals("Invalid Mebibyte to Mibibyte conversion",10f, unit.toMebibytes(10),0);
        Assert.assertEquals("Invalid Mebibyte to Gibibyte conversion",2f, unit.toGibibytes(2048),0);
        Assert.assertEquals("Invalid Mebibyte to Tebibyte conversion",2f, unit.toTebibytes(2097152),0);
        Assert.assertEquals("Invalid Mebibyte to Pebibyte conversion",2f, unit.toPebibytes(2147483648f),0);
        Assert.assertEquals("Invalid Mebibyte to Exbibyte conversion",2f, unit.toExbibytes(2.199023256E12f),0);
        Assert.assertEquals("Invalid Mebibyte to Zebibyte conversion",2f, unit.toZebibytes(2.251799814E15f),0);
        Assert.assertEquals("Invalid Mebibyte to Yobibyte conversion",2f, unit.toYobibytes(2.305843009E18f),0);
    }


    @Test
    public void GibibyteTest() {
        DataUnit unit= DataUnit.GIBIBYTE;
        Assert.assertEquals("Invalid Gibibyte to Bit conversion",4294967296f, unit.toBits(0.5f),0);
        Assert.assertEquals("Invalid Gibibyte to Nibble conversion",134217728f, unit.toNibbles(0.0625f),0);
        Assert.assertEquals("Invalid Gibibyte to Byte conversion",16777216f, unit.toBytes(0.015625f),0);
        Assert.assertEquals("Invalid Gibibyte to Kibibyte conversion",2048, unit.toKibibytes(0.001953125f),0);
        Assert.assertEquals("Invalid Gibibyte to Mibibyte conversion",512, unit.toMebibytes(0.5f),0);
        Assert.assertEquals("Invalid Gibibyte to Gibibyte conversion",12f, unit.toGibibytes(12),0);
        Assert.assertEquals("Invalid Gibibyte to Tebibyte conversion",2f, unit.toTebibytes(2048),0);
        Assert.assertEquals("Invalid Gibibyte to Pebibyte conversion",2f, unit.toPebibytes(2097152),0);
        Assert.assertEquals("Invalid Gibibyte to Exbibyte conversion",2f, unit.toExbibytes(2147483648f),0);
        Assert.assertEquals("Invalid Gibibyte to Zebibyte conversion",2f, unit.toZebibytes(2.199023256E12f),0);
        Assert.assertEquals("Invalid Gibibyte to Yobibyte conversion",2f, unit.toYobibytes(2.251799814E15f),0);
    }


    @Test
    public void TebibyteTest() {
        DataUnit unit= DataUnit.TEBIBYTE;
        Assert.assertEquals("Invalid Tebibyte to Bit conversion",412316860416f, unit.toBits(0.046875f),0);
        Assert.assertEquals("Invalid Tebibyte to Nibble conversion",103079215104f, unit.toNibbles(0.046875f),0);
        Assert.assertEquals("Invalid Tebibyte to Byte conversion",51539607552f, unit.toBytes(0.046875f),0);
        Assert.assertEquals("Invalid Tebibyte to Kibibyte conversion",16777216f, unit.toKibibytes(0.015625f),0);
        Assert.assertEquals("Invalid Tebibyte to Mibibyte conversion",2048, unit.toMebibytes(0.001953125f),0);
        Assert.assertEquals("Invalid Tebibyte to Gibibyte conversion",512f, unit.toGibibytes(0.5f),0);
        Assert.assertEquals("Invalid Tebibyte to Tebibyte conversion",12f, unit.toTebibytes(12),0);
        Assert.assertEquals("Invalid Tebibyte to Pebibyte conversion",2f, unit.toPebibytes(2048),0);
        Assert.assertEquals("Invalid Tebibyte to Exbibyte conversion",2f, unit.toExbibytes(2097152),0);
        Assert.assertEquals("Invalid Tebibyte to Zebibyte conversion",2f, unit.toZebibytes(2147483648f),0);
        Assert.assertEquals("Invalid Tebibyte to Yobibyte conversion",2f, unit.toYobibytes(2.199023256E12f),0);
    }


    @Test
    public void PebibyteTest() {
        DataUnit unit= DataUnit.PEBIBYTE;
        Assert.assertEquals("Invalid Pebibyte to Bit conversion",4.22212465065984E14, unit.toBits(0.046875f),0);
        Assert.assertEquals("Invalid Pebibyte to Nibble conversion",1.05553116266496E14, unit.toNibbles(0.046875f),0);
        Assert.assertEquals("Invalid Pebibyte to Byte conversion",5.2776558133248E13, unit.toBytes(0.046875f),0);
        Assert.assertEquals("Invalid Pebibyte to Kibibyte conversion",51539607552f, unit.toKibibytes(0.046875f),0);
        Assert.assertEquals("Invalid Pebibyte to Mibibyte conversion",16777216f, unit.toMebibytes(0.015625f),0);
        Assert.assertEquals("Invalid Pebibyte to Gibibyte conversion",2048, unit.toGibibytes(0.001953125f),0);
        Assert.assertEquals("Invalid Pebibyte to Tebibyte conversion",512f, unit.toTebibytes(0.5f),0);
        Assert.assertEquals("Invalid Pebibyte to Pebibyte conversion",12f, unit.toPebibytes(12),0);
        Assert.assertEquals("Invalid Pebibyte to Exbibyte conversion",2f, unit.toExbibytes(2048),0);
        Assert.assertEquals("Invalid Pebibyte to Zebibyte conversion",2f, unit.toZebibytes(2097152),0);
        Assert.assertEquals("Invalid Pebibyte to Yobibyte conversion",2f, unit.toYobibytes(2147483648f),0);
    }


    @Test
    public void ExbibyteTest() {
        DataUnit unit= DataUnit.EXBIBYTE;
        Assert.assertEquals("Invalid Exbibyte to Bit conversion",4.3234556422756762E17, unit.toBits(0.046875f),0);
        Assert.assertEquals("Invalid Exbibyte to Nibble conversion",1.08086391056891904E17, unit.toNibbles(0.046875f),0);
        Assert.assertEquals("Invalid Exbibyte to Byte conversion",5.4043195528445952E16, unit.toBytes(0.046875f),0);
        Assert.assertEquals("Invalid Exbibyte to Kibibyte conversion",5.2776558133248E13, unit.toKibibytes(0.046875f),0);
        Assert.assertEquals("Invalid Exbibyte to Mibibyte conversion",51539607552f, unit.toMebibytes(0.046875f),0);
        Assert.assertEquals("Invalid Exbibyte to Gibibyte conversion",16777216f, unit.toGibibytes(0.015625f),0);
        Assert.assertEquals("Invalid Exbibyte to Tebibyte conversion",2048, unit.toTebibytes(0.001953125f),0);
        Assert.assertEquals("Invalid Exbibyte to Pebibyte conversion",512f, unit.toPebibytes(0.5f),0);
        Assert.assertEquals("Invalid Exbibyte to Exbibyte conversion",12f, unit.toExbibytes(12),0);
        Assert.assertEquals("Invalid Exbibyte to Zebibyte conversion",2f, unit.toZebibytes(2048),0);
        Assert.assertEquals("Invalid Exbibyte to Yobibyte conversion",2f, unit.toYobibytes(2097152),0);
    }

}
