package id.my.agungdh.puskes.mapper;

import id.my.agungdh.puskes.dto.KehamilanRequest;
import id.my.agungdh.puskes.dto.KehamilanResponse;
import id.my.agungdh.puskes.entity.Kehamilan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface KehamilanMapper {

    @Mapping(target = "userId", source = "user.uuid")
    @Mapping(target = "namaIbu", source = "user.name")
    KehamilanResponse toResponse(Kehamilan kehamilan);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    Kehamilan fromRequest(KehamilanRequest request);
}
