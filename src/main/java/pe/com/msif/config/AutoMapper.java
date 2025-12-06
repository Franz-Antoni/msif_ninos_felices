package pe.com.msif.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AutoMapper {
    @Autowired
    private ModelMapper modelMapper;

    public <S, D> List<D> mapList(List<S> source, Class<D> targetClass) {
        return source.stream().map(element -> modelMapper.map(element, targetClass)).toList();
    }

    public <S, D> D mapTo(S source, Class<D> targetClass) {
        return modelMapper.map(source, targetClass);
    }
}
