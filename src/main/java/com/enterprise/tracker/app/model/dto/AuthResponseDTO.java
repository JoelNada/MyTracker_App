package com.enterprise.tracker.app.model.dto;

import lombok.*;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDTO {
    String token;
    String username;
    Set<String> role;
}
