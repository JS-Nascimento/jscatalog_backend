    package js.dev.jstec.jscatalog_backend.service.implemantation;

    import js.dev.jstec.jscatalog_backend.domain.entities.Category;
    import js.dev.jstec.jscatalog_backend.domain.repositories.CategoryRepository;
    import js.dev.jstec.jscatalog_backend.rest.DTOS.CategoryDTO;
    import js.dev.jstec.jscatalog_backend.service.CategoryService;
    import js.dev.jstec.jscatalog_backend.service.exception.DatabaseIntegrityException;
    import js.dev.jstec.jscatalog_backend.service.exception.ResourceNotFoundException;
    import org.modelmapper.ModelMapper;
    import org.springframework.dao.DataIntegrityViolationException;
    import org.springframework.dao.EmptyResultDataAccessException;
    import org.springframework.data.domain.*;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;
    import org.springframework.web.bind.annotation.RequestParam;

    import javax.persistence.EntityNotFoundException;
    import java.time.LocalDate;
    import java.util.ArrayList;
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
//                 Implementação atraves do pageImpl mas esta com erro no Pageable
//                @Transactional(readOnly = true)
//                public PageImpl <CategoryDTO> findAllPaged ( PageRequest pageRequest) {
//
//                   return new PageImpl <CategoryDTO> (repository.findAll(pageRequest)
//                           .stream()
//                           .map( category -> modelMapper.map( category, CategoryDTO.class ) )
//                           .collect( Collectors.toList())
//                   ) ;
//
//                }
                @Transactional(readOnly = true)
                public Page <CategoryDTO> findAllPaged ( Pageable pageable ) {
                        Page<Category> list = repository.findAll(pageable);
                            return list.map( category -> modelMapper.map( category, CategoryDTO.class ) );
                }

                @Transactional(readOnly = true)
                        public CategoryDTO findById ( Integer id ) {
                            return repository.findById( id )
                                    .map( category -> modelMapper.map( category, CategoryDTO.class ) )
                                    .orElseThrow( () -> new ResourceNotFoundException("Categoria não existe." ) );
                        }

                @Override
                @Transactional
                public CategoryDTO create ( CategoryDTO dto ) {
                    Category category = repository.save( modelMapper.map( dto, Category.class  ) );
                    return modelMapper.map( category, CategoryDTO.class  );
                }

                @Override
                @Transactional
                public CategoryDTO update (Integer id,  CategoryDTO dto ) {
                   try {
                       Category category = repository.getReferenceById( id );
                            category.setName( dto.getName() );
                            category.setUpdated_Date( LocalDate.now() );

                       return modelMapper.map( repository.save( category ) , CategoryDTO.class );

                   } catch (EntityNotFoundException e) {
                        throw new ResourceNotFoundException( "Categoria não encontrada." );
                   }

                }

                @Override
                public void delete ( Integer id ) {
                   try {
                       repository.deleteById( id );

                   } catch (EmptyResultDataAccessException e) {
                       throw new ResourceNotFoundException( "Categoria não encontrada." );
                   } catch (DataIntegrityViolationException e) {
                       throw new DatabaseIntegrityException( "Violação de integridade relacional.");
                   }

                }

    }
