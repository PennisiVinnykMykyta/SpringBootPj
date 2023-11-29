package com.nikita.springbootpj.repositories;

import com.nikita.springbootpj.entities.FileData;
import com.nikita.springbootpj.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileDataRepository extends JpaRepository<FileData,Integer> {
    FileData findFileDataByUser(User user);
}
