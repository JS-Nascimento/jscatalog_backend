package js.dev.jstec.jscatalog_backend.service.implemantation;

import js.dev.jstec.jscatalog_backend.domain.entities.Category;
import js.dev.jstec.jscatalog_backend.domain.repositories.CategoryRepository;
import js.dev.jstec.jscatalog_backend.rest.DTOS.CategoryDTO;
import js.dev.jstec.jscatalog_backend.service.CategoryService;
import js.dev.jstec.jscatalog_backend.service.exception.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
            private ModelMapper modelMapper;
            private CategoryRepository repository;

           public CategoryServiceImpl ( CategoryRepository repository, ModelMapper modelMapper  ) {
                this.repository = repository;
                this.modelMapper = modelMapper;
            }

            @Transactional(readOnly = true)
            public List <CategoryDTO> findAll () {
               return new ArrayList <>(repository.findAll()
                        .stream()
                        .map( category -> modelMapper.map( category, CategoryDTO.class )  )
                        .collect( Collectors.toList()));

            }
            @Transactional(readOnly = true)
            public CategoryDTO findById ( Integer Id ) {
                return repository.findById( Id )
                        .map( category -> modelMapper.map( category, CategoryDTO.class ) )
                        .orElseThrow( () -> new EntityNotFoundException("Categoria não existe." ) );
            }

            @Override
            @Transactional(readOnly = true)
            public CategoryDTO create ( CategoryDTO dto ) {
                dto.setCreated_Date( LocalDate.now() );
                Category category = repository.save( modelMapper.map( dto, Category.class  ) );
                return modelMapper.map( category, CategoryDTO.class  );
            }

}
