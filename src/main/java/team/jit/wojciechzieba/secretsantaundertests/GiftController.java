package team.jit.wojciechzieba.secretsantaundertests;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
class GiftController {

    public GiftController(final GiftService giftService) {
        this.giftService = giftService;
    }

    private final GiftService giftService;

    @PostMapping("/gifts")
    @ResponseStatus(HttpStatus.OK)
    String registerGift() {
        return giftService.registerGift();
    }
}
