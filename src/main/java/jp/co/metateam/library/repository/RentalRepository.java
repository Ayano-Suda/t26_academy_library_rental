package jp.co.metateam.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.metateam.library.model.RentalManage;

@Repository
public interface RentalRepository
        extends JpaRepository<RentalManage, Long> {

}