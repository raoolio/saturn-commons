package com.saturn.common.test;

import com.saturn.commons.utils.file.DataSizeUnit;
import junit.framework.Assert;
import org.junit.Test;


/**
 * DataSizeUnit testing
 */
public class DataSizeUnitTest {


    @Test
    public void test1() {

        Assert.assertEquals("Byte -> Bits conversion failed", 8f, DataSizeUnit.BYTE.toBits(1));
        Assert.assertEquals("Byte -> Nibbles conversion failed", 4f, DataSizeUnit.BYTE.toNibbles(2));
        Assert.assertEquals("Byte -> Byte conversion failed", 5f, DataSizeUnit.BYTE.toBytes(5));
        Assert.assertEquals("Byte -> KiloByte conversion failed", 2f, DataSizeUnit.BYTE.toKiloBytes(2048));
        Assert.assertEquals("Byte -> MegaByte conversion failed", 2f, DataSizeUnit.BYTE.toMegaBytes(2097152));
        Assert.assertEquals("Byte -> GigaByte conversion failed", 7f, DataSizeUnit.BYTE.toGigaBytes(7516192768l));
        Assert.assertEquals("Byte -> TeraByte conversion failed", 0.005859375f, DataSizeUnit.BYTE.toTeraBytes(6442450944l));
        Assert.assertEquals("Byte -> PetaByte conversion failed", 2f, DataSizeUnit.BYTE.toPetaBytes(2251799813685248l));
        Assert.assertEquals("Byte -> ExaByte conversion failed", 0.001953125f, DataSizeUnit.BYTE.toExaBytes(2251799813685248l));
        Assert.assertEquals("Byte -> ZettaByte conversion failed", 0.0000019073486328125f, DataSizeUnit.BYTE.toZettaBytes(2251799813685248l));
        Assert.assertEquals("Byte -> YottaByte conversion failed", 1f, DataSizeUnit.GIGABYTE.toYottaBytes(1125899906842624l));
        Assert.assertEquals("Byte -> BrontoByte conversion failed", 0.0009765625f, DataSizeUnit.GIGABYTE.toBrontoBytes(1125899906842624l));
        Assert.assertEquals("Byte -> GeopByte conversion failed", 0.00000095367431640625f, DataSizeUnit.GIGABYTE.toGeopBytes(1125899906842624l));

    }

}
