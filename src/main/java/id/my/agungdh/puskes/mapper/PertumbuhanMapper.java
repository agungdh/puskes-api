package id.my.agungdh.puskes.mapper;

import id.my.agungdh.puskes.dto.PertumbuhanRequest;
import id.my.agungdh.puskes.dto.PertumbuhanResponse;
import id.my.agungdh.puskes.entity.Pertumbuhan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PertumbuhanMapper {

    @Mapping(target = "kehamilanUuid", source = "kehamilan.uuid")
    @Mapping(target = "statusImt", ignore = true)
    PertumbuhanResponse toResponse(Pertumbuhan pertumbuhan);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "kehamilan", ignore = true)
    @Mapping(target = "imt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    Pertumbuhan fromRequest(PertumbuhanRequest request);
}
