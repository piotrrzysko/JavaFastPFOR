/**
 * This code is released under the
 * Apache License Version 2.0 http://www.apache.org/licenses/.
 *
 * (c) Daniel Lemire, http://lemire.me/en/
 */

package me.lemire.longcompression;

import org.junit.Assert;
import org.junit.Test;

/**
 * Edge-cases having caused issue specifically with LongVariableByte.
 * 
 * @author Benoit Lacelle
 */
public class TestLongVariableByte extends ATestLongCODEC {
	final LongVariableByte codec = new LongVariableByte();

	@Override
	public LongCODEC getCodec() {
		return codec;
	}

	@Test
	public void testCodec_allBitWidths() {
		for (int bitWidth = 0; bitWidth <= 64; bitWidth++) {
			long value = bitWidth == 0 ? 0 : 1L << (bitWidth - 1);

			int expectedSizeInBytes = Math.max(1, (bitWidth + 6) / 7);
			int expectedSizeInLongs = (expectedSizeInBytes > 8) ? 2 : 1;

			Assert.assertEquals(expectedSizeInLongs, LongTestUtils.compress((LongCODEC) codec, new long[] { value }).length);
			Assert.assertEquals(expectedSizeInBytes, LongTestUtils.compress((ByteLongCODEC) codec, new long[] { value }).length);
			Assert.assertEquals(expectedSizeInLongs,
					LongTestUtils.compressHeadless((SkippableLongCODEC) codec, new long[] { value }).length);
		}
	}
}
