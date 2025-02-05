package guru.springframework.services;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Transactional
    @Override
    public void saveImageFile(Long id, MultipartFile file) {
        log.info("saveImageFile - ");

        try {
            Recipe recipe = recipeRepository.findById(id).get();
            Byte[] bytesObject = new Byte[file.getBytes().length];

            int i = 0;

            for (byte b : file.getBytes()){
                bytesObject[i++] = b;
            }

            recipe.setImage(bytesObject);
            recipeRepository.save(recipe);
            log.info("saveImageFile - recipe saved");
        } catch (IOException e) {
            log.error("Error", e);
            e.printStackTrace();
        }
        log.info("saveImageFile - end");
    }
}
