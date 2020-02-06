package com.codeforcommunity.processor;

import com.codeforcommunity.dto.notes.ContentNote;
import org.jooq.generated.tables.records.NoteRecord;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NoteMapperTest {

    @Test
    public void contentNoteToNoteRecord() {
        ContentNote contentNote = new ContentNote("title", "content");
        NoteRecord noteRecord = NoteMapper.INSTANCE.contentNoteToNoteRecord(contentNote);
        assertNotNull(noteRecord);
        assertEquals("content", noteRecord.getBody());
        assertEquals("title", noteRecord.getTitle());
    }
}