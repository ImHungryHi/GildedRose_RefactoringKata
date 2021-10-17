package com.gildedrose;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {
    @Test
    void should_Reset_WhenBackstagePasses_Expire() {
        // Given
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 0, 49) };
        GildedRose app = new GildedRose(items);

        // When
        app.updateQuality();

        // Then
        assertAll(
            () -> assertEquals(-1, app.items[0].sellIn),
            () -> assertEquals(0, app.items[0].quality)
        );
    }
}
