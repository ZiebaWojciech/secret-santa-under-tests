package team.jit.wojciechzieba.secretsantaundertests;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
class GiftController {

    @PostMapping("/gifts")
    @ResponseStatus(HttpStatus.OK)
    String registerGift() {
        return "Wojtek";
    }
}
