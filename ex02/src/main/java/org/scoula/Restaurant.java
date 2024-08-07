package org.scoula;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.Setter;

@Component
@Data
@RequiredArgsConstructor
public class Restaurant {
//    @Autowired
    final private Chef chef;
}
