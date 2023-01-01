    package js.dev.jstec.jscatalog_backend.service.implemantation;

    import js.dev.jstec.jscatalog_backend.domain.entities.Product;
    import js.dev.jstec.jscatalog_backend.domain.repositories.ProductRepository;
    import js.dev.jstec.jscatalog_backend.rest.DTOS.ProductDTO;
    import js.dev.jstec.jscatalog_backend.service.ProductService;
    import js.dev.jstec.jscatalog_backend.service.exception.DatabaseIntegrityException;
    import js.dev.jstec.jscatalog_backend.service.exception.ResourceNotFoundException;
    import org.modelmapper.ModelMapper;
    import org.springframework.dao.DataIntegrityViolationException;
    import org.springframework.dao.EmptyResultDataAccessException;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.PageRequest;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;

    import javax.persistence.EntityNotFoundException;

    @Service
    public class ProductServiceImpl implements ProductService {
                private final ModelMapper modelMapper;
                private final ProductRepository repository;

               public ProductServiceImpl ( ProductRepository repository, ModelMapper modelMapper  ) {
                    this.repository = repository;
                    this.modelMapper = modelMapper;
                }
             
                @Transactional(readOnly = true)
                public Page <ProductDTO> findAllPaged ( PageRequest pageRequest) {
                        Page <Product> list = repository.findAll(pageRequest);
                            return list.map(  product -> (new ProductDTO(product, product.getCategories()))  );
                }

                @Transactional(readOnly = true)
                        public ProductDTO findById ( Integer id ) {
                            return repository.findById( id )
                                    .map( product -> (new ProductDTO(product, product.getCategories())) )
                                    .orElseThrow( () -> new ResourceNotFoundException("Categoria não existe." ) );
                        }

                @Override
                @Transactional
                public ProductDTO create ( ProductDTO dto ) {
                   Product product = repository.save( modelMapper.map( dto, Product.class  ) );
                   return modelMapper.map( product, ProductDTO.class  );
                }

                @Override
                @Transactional
                public ProductDTO update (Integer id,  ProductDTO dto ) {
                   try {
                       Product product = repository.getReferenceById( id );
                            product.setName( dto.getName() );


                       return modelMapper.map( repository.save( product ) , ProductDTO.class );

                   } catch (EntityNotFoundException e) {
                        throw new ResourceNotFoundException( "Produto não encontrado." );
                   }

                }

                @Override
                public void delete ( Integer id ) {
                   try {
                       repository.deleteById( id );

                   } catch (EmptyResultDataAccessException e) {
                       throw new ResourceNotFoundException( "Produto não encontrado." );
                   } catch (DataIntegrityViolationException e) {
                       throw new DatabaseIntegrityException( "Violação de integridade relacional.");
                   }

                }

    }
