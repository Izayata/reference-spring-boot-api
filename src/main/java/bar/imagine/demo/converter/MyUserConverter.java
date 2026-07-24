package bar.imagine.demo.converter;

import bar.imagine.demo.data.MyUser;
import bar.imagine.demo.dto.EmailDTO;
import bar.imagine.demo.dto.MyUserDTO;
import bar.imagine.demo.dto.myUser.MyUsernameDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MyUserConverter {
    private final CustomerConverter customerConverter;

    public MyUserDTO convertMyUserToMyUserDto(MyUser myUser) {
        return MyUserDTO.builder()
            .email(new EmailDTO(myUser.getEmail().getValue()))
            .myUsername(new MyUsernameDTO(myUser.getMyUsername().getValue()))
            .customer(myUser.getCustomer() != null
                ? customerConverter.convertCustomerToCustomerDto(myUser.getCustomer()) : null)
            .build();
    }
}
