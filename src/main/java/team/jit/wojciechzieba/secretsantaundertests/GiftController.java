package team.jit.wojciechzieba.secretsantaundertests;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    GiftRegistrationResponse registerGift(@RequestBody GiftRegistrationCommand registrationCommand) {

        return giftService.registerGift(registrationCommand);
    }
}
