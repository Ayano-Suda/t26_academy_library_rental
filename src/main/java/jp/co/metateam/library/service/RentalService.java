package jp.co.metateam.library.service;

import org.springframework.stereotype.Service;

import jp.co.metateam.library.model.RentalDto;
import jp.co.metateam.library.model.RentalManage;
import jp.co.metateam.library.repository.RentalRepository;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;

    public RentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public void save(RentalDto rentalDto) {

        RentalManage rentalManage = new RentalManage();

        rentalManage.setEmployeeId(rentalDto.getEmployeeId());
        rentalManage.setStockId(rentalDto.getStockId());
        rentalManage.setStatus(rentalDto.getStatus());
        rentalManage.setExpectedRentalOn(rentalDto.getExpectedRentalOn());
        rentalManage.setExpectedReturnOn(rentalDto.getExpectedReturnOn());

        rentalRepository.save(rentalManage);
    }
}
