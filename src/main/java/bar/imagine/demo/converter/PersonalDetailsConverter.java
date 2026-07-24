package bar.imagine.demo.converter;

import bar.imagine.demo.data.customer.PersonalDetails;
import bar.imagine.demo.data.customer.personalDetails.Firstname;
import bar.imagine.demo.data.customer.personalDetails.Lastname;
import bar.imagine.demo.data.customer.personalDetails.PhoneNumber;
import bar.imagine.demo.dto.customer.PersonalDetailsDTO;
import bar.imagine.demo.dto.customer.personalDetails.FirstnameDTO;
import bar.imagine.demo.dto.customer.personalDetails.LastnameDTO;
import bar.imagine.demo.dto.customer.personalDetails.PhoneNumberDTO;
import org.springframework.stereotype.Component;

@Component
public class PersonalDetailsConverter {
    public PersonalDetails convertPersonalDetailsDtoToPersonalDetails(PersonalDetailsDTO personalDetailsDto) {
        return PersonalDetails.builder()
                .firstname(new Firstname(personalDetailsDto.getFirstname().getValue()))
                .lastname(new Lastname(personalDetailsDto.getLastname().getValue()))
                .phoneNumber(new PhoneNumber(personalDetailsDto.getPhoneNumber().getValue()))
                .build();
    }

    public PersonalDetailsDTO convertPersonalDetailsToPersonalDetailsDto(PersonalDetails personalDetails) {
        return PersonalDetailsDTO.builder()
            .firstname(new FirstnameDTO(personalDetails.getFirstname().getValue()))
            .lastname(new LastnameDTO(personalDetails.getLastname().getValue()))
            .phoneNumber(new PhoneNumberDTO(personalDetails.getPhoneNumber().getValue()))
            .build();
    }
}
