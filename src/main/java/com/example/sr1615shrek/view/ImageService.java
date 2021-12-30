package com.example.sr1615shrek.view;

import com.example.sr1615shrek.entity.Entity;
import com.example.sr1615shrek.entity.model.Dalek;
import com.example.sr1615shrek.entity.model.Doctor;
import com.example.sr1615shrek.entity.model.Junk;
import javafx.scene.image.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class ImageService {

    private static final String DALEK_GRAPHIC_PATH = "images/robot.png";

    private static final String DOCTOR_GRAPHIC_PATH = "images/shrek.png";

    private static final String JUNK_GRAPHIC_PATH = "images/junk.png";

    private final Map<Type, Image> imageMap = new HashMap<>();

    public ImageService(){
        initMap();
    }

    private void initMap(){;
        this.imageMap.put(Dalek.class, loadImage(DALEK_GRAPHIC_PATH));
        this.imageMap.put(Doctor.class, loadImage(DOCTOR_GRAPHIC_PATH));
        this.imageMap.put(Junk.class, loadImage(JUNK_GRAPHIC_PATH));
    }

    private Image loadImage(String imagePath){
        return new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(imagePath)));
    }

    public Image getEntityImage(Entity entity){
        return this.imageMap.get(entity.getClass());
    }
}
