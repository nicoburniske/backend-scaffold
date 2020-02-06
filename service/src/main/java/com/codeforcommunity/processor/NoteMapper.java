package com.codeforcommunity.processor;

import com.codeforcommunity.dto.notes.ContentNote;
import org.jooq.generated.tables.records.NoteRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NoteMapper {

    NoteMapper INSTANCE = Mappers.getMapper(NoteMapper.class);

    @Mapping(source = "content", target = "body")
    NoteRecord contentNoteToNoteRecord(ContentNote contentNote);
}
