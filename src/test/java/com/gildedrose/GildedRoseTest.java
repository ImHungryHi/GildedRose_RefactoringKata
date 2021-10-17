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

    @Test
    void should_MaxAt50_WhenBackstagePassesRipen() {
        // Given
        Item[] items = new Item[] {
            new Item("Backstage passes to a TAFKAL80ETC concert", 11, GildedRose.MAX_QUALITY),
            new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49),
            new Item("Backstage passes to a TAFKAL80ETC concert", 5, 48)
        };
        GildedRose app = new GildedRose(items);

        // When
        app.updateQuality();

        // Then
        assertAll(
            () -> assertEquals(GildedRose.MAX_QUALITY, app.items[0].quality),
            () -> assertEquals(GildedRose.MAX_QUALITY, app.items[1].quality),
            () -> assertEquals(GildedRose.MAX_QUALITY, app.items[2].quality)
        );
    }

    @Test
    void should_Increment_OnceIfSellInPositive_TwiceIfSellInNegative_WhenAgedBrieRipens() {
        // Given
        Item[] items = new Item[] {
            new Item("Aged Brie", 2, 0),
            new Item("Aged Brie", 1, 0),
            new Item("Aged Brie", 0, 0),
            new Item("Aged Brie", -1, 0)
        };
        GildedRose app = new GildedRose(items);

        // When
        app.updateQuality();

        // Then
        assertAll(
            () -> assertEquals(1, app.items[0].quality),
            () -> assertEquals(1, app.items[1].quality),
            () -> assertEquals(2, app.items[2].quality),
            () -> assertEquals(2, app.items[3].quality)
        );
    }

    @Test
    void should_MaxAt50_WhenAgedBrieRipens() {
        // Given
        Item[] items = new Item[] {
            new Item("Aged Brie", 2, 50),
            new Item("Aged Brie", -1, 49)
        };
        GildedRose app = new GildedRose(items);

        // When
        app.updateQuality();

        // Then
        assertAll(
            () -> assertEquals(GildedRose.MAX_QUALITY, app.items[0].quality),
            () -> assertEquals(GildedRose.MAX_QUALITY, app.items[1].quality)
        );
    }
}
