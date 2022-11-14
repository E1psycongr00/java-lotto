package lotto.layer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mockStatic;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lotto.domain.Lotto;
import lotto.domain.LottoNumber;
import lotto.domain.Money;
import lotto.domain.WinningLotto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;


class LottoServiceTest {

    private final Table table = new Table();
    private final LottoService lottoService = new LottoService(table);

    @DisplayName("금액/1000 만큼 로또가 생성되야함")
    @Test
    void lottoServiceGenerateLotteriesByMoney() {
        // given
        int price = 2000;
        Money money = new Money(price);

        // when
        List<Lotto> lotteries = lottoService.generateLotteries(money);

        // then
        assertThat(lotteries.size()).isEqualTo(price / 1000);
    }

    @DisplayName("생성된 로또 영속화 테스트")
    @Test
    void lottoServicePersistTest() {
        // given
        int price = 2000;
        Money money = new Money(price);

        // when
        List<Lotto> lotteries = lottoService.generateLotteries(money);
        List<Lotto> tableLotteries = table.getLotteries();

        // then
        assertThat(lotteries.size()).isEqualTo(tableLotteries.size());
    }

    @DisplayName("당첨 빈도수가 0이여도 모든 당첨이 key 값에 존재해야함")
    @Test
    void lottoServiceMakeFrequencyMustHasAllWinningLottoKeys() {
        // given
        MockedStatic<Randoms> randoms = mockStatic(Randoms.class);
        List<Integer> expectValues = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6));
        Lotto inputLotto = new Lotto(expectValues);
        LottoNumber bonusNumber = LottoNumber.getInstance(7);
        given(Randoms.pickUniqueNumbersInRange(1, 45, 6)).willReturn(expectValues);

        // when
        Map<WinningLotto, Integer> frequency = lottoService.getWinningLottoFrequency(inputLotto,
                bonusNumber);

        // then
        assertThat(frequency).containsKeys(WinningLotto.values());
        randoms.close();
    }


}