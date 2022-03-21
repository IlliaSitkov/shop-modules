package com.databases.shop.mapstruct.mappers;

import com.databases.shop.mapstruct.dtos.provider.ProviderGetDto;
import com.databases.shop.mapstruct.dtos.provider.ProviderPostDto;
import com.databases.shop.mapstruct.dtos.provider.ProviderPutDto;
import com.databases.shop.mapstruct.dtos.provider.ProviderSaveDto;
import com.databases.shop.models.Provider;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProviderMapper {

    ProviderGetDto providerToProviderGetDto(Provider provider);

    Iterable<ProviderGetDto> providersToProvidersGetDto(Iterable<Provider> providers);

    ProviderSaveDto providerPostDtoToProviderSaveDto(ProviderPostDto providerPostDto);

    Provider providerPutDtoToProvider(ProviderPutDto providerPutDto);

    Provider providerSaveDtoToProvider(ProviderSaveDto providerSaveDto);
}
