package automappingobjects.service;

import automappingobjects.entity.Address;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AddressService {
    List<Address> getAllAddresss();
    Address getAddressById(Long id);
    Address addAddress(Address address);
    Address updateAddress(Address address);
    Address deleteAddress(Long id);
    long getAddressCount();
}
