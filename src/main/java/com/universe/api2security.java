package com.universe;

import com.universe.usersSecurity.persistence.entity.PermissionEntity;
import com.universe.usersSecurity.persistence.entity.RoleEntity;
import com.universe.usersSecurity.persistence.entity.RoleEnum;
import com.universe.usersSecurity.persistence.entity.UserEntity;
import com.universe.usersSecurity.persistence.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;
import java.util.Set;

@SpringBootApplication
@EnableJpaAuditing
public class api2security {

    public static void main(String[] args) {
        SpringApplication.run(api2security.class, args);
    }

//    @Bean
//    CommandLineRunner init(UserRepository userRepository) {
//        return args -> {
//            /* Create PERMISSIONS */
//            PermissionEntity createPermission = PermissionEntity.builder()
//                    .name("CREATE")
//                    .build();
//
//            PermissionEntity readPermission = PermissionEntity.builder()
//                    .name("READ")
//                    .build();
//
//            PermissionEntity updatePermission = PermissionEntity.builder()
//                    .name("UPDATE")
//                    .build();
//
//            PermissionEntity deletePermission = PermissionEntity.builder()
//                    .name("DELETE")
//                    .build();
//
//            PermissionEntity rootPermission = PermissionEntity.builder()
//                    .name("ROOT")
//                    .build();
//
//            /* Create ROLES */
//            RoleEntity roleAdmin = RoleEntity.builder()
//                    .roleEnum(RoleEnum.ADMIN)
//                    .permissionList(Set.of(createPermission, readPermission, updatePermission, deletePermission, rootPermission))
//                    .build();
//
//            RoleEntity roleUser = RoleEntity.builder()
//                    .roleEnum(RoleEnum.USER)
//                    .permissionList(Set.of(createPermission, readPermission))
//                    .build();
//
//            RoleEntity roleInvited = RoleEntity.builder()
//                    .roleEnum(RoleEnum.INVITED)
//                    .permissionList(Set.of(readPermission))
//                    .build();
//
//            RoleEntity roleDeveloper = RoleEntity.builder()
//                    .roleEnum(RoleEnum.DEVELOPER)
//                    .permissionList(Set.of(createPermission, readPermission, updatePermission, deletePermission))
//                    .build();
//
//            /* CREATE USERS */
//            UserEntity userNavidad = UserEntity.builder()
//                    .username("navidad")
//                    .password("$2a$10$x.vWnlK1B9IkOtqvhUn0gOHCMui66./JPbQRCXE78BSE8uDRZIyOm")
//                    .isEnabled(true)
//                    .accountNoExpired(true)
//                    .accountNoLocked(true)
//                    .credentialNoExpired(true)
//                    .roles(Set.of(roleAdmin))
//                    .build();
//
//            UserEntity userCarmen = UserEntity.builder()
//                    .username("carmen")
//                    .password("$2a$10$x.vWnlK1B9IkOtqvhUn0gOHCMui66./JPbQRCXE78BSE8uDRZIyOm")
//                    .isEnabled(true)
//                    .accountNoExpired(true)
//                    .accountNoLocked(true)
//                    .credentialNoExpired(true)
//                    .roles(Set.of(roleUser))
//                    .build();
//
//            UserEntity userRamon = UserEntity.builder()
//                    .username("ramon")
//                    .password("$2a$10$x.vWnlK1B9IkOtqvhUn0gOHCMui66./JPbQRCXE78BSE8uDRZIyOm")
//                    .isEnabled(true)
//                    .accountNoExpired(true)
//                    .accountNoLocked(true)
//                    .credentialNoExpired(true)
//                    .roles(Set.of(roleInvited))
//                    .build();
//
//            UserEntity userPablo = UserEntity.builder()
//                    .username("pablo")
//                    .password("$2a$10$x.vWnlK1B9IkOtqvhUn0gOHCMui66./JPbQRCXE78BSE8uDRZIyOm")
//                    .isEnabled(true)
//                    .accountNoExpired(true)
//                    .accountNoLocked(true)
//                    .credentialNoExpired(true)
//                    .roles(Set.of(roleDeveloper))
//                    .build();
//
//            userRepository.saveAll(List.of(userNavidad, userPablo, userCarmen, userRamon));
//        };
//    }

}
