package com.moneyplay.MoneyPlay.service.WordService;

import com.moneyplay.MoneyPlay.domain.Word;
import com.moneyplay.MoneyPlay.repository.WordRepository;
import lombok.RequiredArgsConstructor;

// 단어 생성 클래스

@RequiredArgsConstructor
public class CreateWords {

    final WordRepository wordRepository;


    public void CreateWords() {

        String[][] word = {
                {"예금", "돈을 은행에 맡겨 이자를 받는 행위입니다."},
                {"대출", "은행이나 금융 기관으로부터 돈을 빌려 사용하는 것입니다."},
                {"이자", "대출이나 예금에 대한 추가 비용으로 돈의 사용에 대한 보상입니다."},
                {"저축", "돈을 모아 미래를 위해 적립하는 것입니다."},
                {"투자", "돈을 어떤 사업이나 자산에 넣어 수익을 얻는 것입니다."},
                {"수익", "투자나 사업 등으로 얻는 돈이나 이득입니다."},
                {"지출", "돈을 소비하거나 사용하는 것을 의미합니다."},
                {"예산", "수입과 지출을 계획하여 관리하는 금액입니다."},
                {"부채", "빌린 돈이나 지불해야 할 돈을 의미합니다."},
                {"자산", "소유하고 있는 돈, 부동산, 자동차 등의 가치 있는 물건입니다."},
                {"재무상태", "개인이나 회사의 재산과 부채의 상태를 나타냅니다."},
                {"월급", "일하는 사람들이 주기적으로 받는 급여입니다."},
                {"카드", "돈을 지불하는 데 사용되는 신용카드나 직불카드를 의미합니다."},
                {"환율", "한 나라의 통화를 다른 나라의 통화로 교환할 때의 비율입니다."},
                {"주식", "회사의 소유권을 나타내는 자본을 나타내는 증권입니다."},
                {"이체", "은행 계좌 간에 돈을 옮기는 것을 의미합니다."},
                {"수표", "은행에서 출금을 위해 발행하는 화이트 페이퍼입니다."},
                {"경제", "돈,자원,생산 소비 등과 관련된 시스템이나 활동을 의미합니다."},
                {"금리", "돈을 빌릴 때 지불해야 하는 추가 비용입니다."},
                {"연금", "노후에 사용할 수 있도록 돈을 모아놓는 제도입니다."},
                {"수수료", "금융 거래나 서비스를 이용할 때 지불해야 하는 비용입니다."},
                {"할인율", "상품이나 서비스 가격에서 할인 받는 비율입니다."},
                {"세금", "정부에 지불하는 부가적인 돈으로 주로 소득이나 소비에 부과됩니다."},
                {"예적금", "일정 기간 동안 돈을 은행에 맡기는 것으로 이자를 받습니다."},
                {"가치평가", "자산이나 투자의 가치를 평가하는 것을 의미합니다."},
                {"재무제표", "회사의 재정 상태를 요약하여 보여주는 문서입니다."},
                {"선물", "물건이나 돈을 선물로 주고 받는 것을 의미합니다."},
                {"적금", "정기적으로 돈을 저축하는 제도로 이자를 받습니다."},
                {"중개인", "부동산이나 금융 거래를 중개하는 사람을 의미합니다."},
                {"외환시장", "다양한 통화를 교환하는 시장을 의미합니다."},
                {"소득", "일을 하거나 투자 등으로 얻는 돈을 의미합니다."},
                {"소비", "돈을 사용하여 상품이나 서비스를 구매하는 행위입니다."},
                {"보험", "미래의 불확실한 사건에 대비하기 위해 가입하는 금융 제품입니다."},
                {"자동이체", "일정한 시간마다 자동으로 돈을 이체하는 것을 의미합니다."},
                {"펀드", "여러 투자자의 돈을 모아 투자하는 금융 상품입니다."},
                {"중금리", "저금리와 고금리 사이의 중간 수준의 금리를 의미합니다."},
                {"부동산", "땅이나 건물과 같은 유형의 자산을 의미합니다."},
                {"대외채무", "외국에서 빌린 돈이나 부채를 의미합니다."},
                {"거래", "상품이나 서비스를 교환하는 금융 활동을 의미합니다."},
                {"신용", "믿음이나 신뢰를 바탕으로 돈을 빌리거나 상품을 구매하는 것을 의미합니다."},
                {"신용점수", "개인의 신용 기록을 기반으로 산정되는 점수로,신용력을 나타냅니다."},
                {"연체료", "대출이나 카드 사용 시 연체되면 지불해야 하는 비용입니다."},
                {"재무계획", "개인이나 가정의 재정 상태를 분석하고 계획하는 것입니다."},
                {"중계수수료", "주식 거래 등을 중개하는 데 지불하는 수수료입니다."},
                {"자본", "회사의 소유주에게 속하는 모든 자산을 의미합니다."},
                {"주택담보대출", "주택을 담보로 받는 대출을 의미합니다."},
                {"투자자", "자금을 어떤 사업이나 자산에 투자하는 사람을 의미합니다."},
                {"중소기업", "작은 규모의 기업을 의미합니다."},
                {"재고", "상품을 보유하고 있는 양을 의미합니다."},
                {"할부", "물건이나 서비스를 나누어 지불하는 방식을 의미합니다."},
                {"자산배분", "투자 포트폴리오에서 각 자산의 비율을 결정하는 것을 의미합니다."},
                {"이익", "비즈니스나 투자에서 얻는 긍정적인 재정적 결과를 의미합니다."},
                {"잠재고객", "제품이나 서비스에 관심이 있는 잠재적인 고객을 의미합니다."},
                {"사기", "부정한 방법으로 돈을 획득하려는 행위를 의미합니다."},
                {"퇴직연금", "노후를 대비하여 근로자가 받는 연금을 의미합니다."},
                {"자본금", "회사 설립 시 주주에 의해 제공되는 초기 자본을 의미합니다."},
                {"배당", "주식의 소유주에게 지급되는 이익을 의미합니다."},
                {"가계부채", "가정이나 개인이 빌린 돈이나 부채를 의미합니다."},
                {"환불", "상품이나 서비스를 반환하거나 취소하여 받는 돈을 의미합니다."},
                {"가치투자", "저평가된 자산을 구매하여 장기적인 투자를 의미합니다."},
                {"리스", "물건을 빌려 사용하거나 임대하는 것을 의미합니다."},
                {"비용", "생산이나 사업 운영을 위해 지출하는 돈을 의미합니다."},
                {"손익계산서", "회사의 수익과 비용을 요약하여 보여주는 문서입니다."},
                {"보증금", "특정 상황에서 지불하기 위해 보관하는 돈을 의미합니다."},
                {"자동차 대출", "자동차를 구매하기 위해 빌리는 대출을 의미합니다."},
                {"회계", "돈의 흐름을 기록하고 분석하는 과정을 의미합니다."},
                {"자동차 보험", "차량 사고 등에 대비하여 가입하는 보험을 의미합니다."},
                {"경기침체", "경제 활동이 둔화되는 상황을 의미합니다."},
                {"원화", "한국의 통화를 의미합니다."},
                {"달러", "미국의 통화를 의미합니다."},
                {"적립식 투자", "일정 기간마다 돈을 모아 투자하는 방식을 의미합니다."},
                {"투자 수익률", "투자한 돈으로 얻는 수익의 비율을 의미합니다."},
                {"경기회복", "경제 활동이 회복되는 상황을 의미합니다."},
                {"화폐", "국가에서 발행하는 돈을 의미합니다."}


        };

        for(int i=0; i<word.length; i++){

            String mean = word[i][0];
            String content = word[i][1];

            Word temp = new Word();

            temp.setWordName(mean);
            temp.setContent(content);

            wordRepository.save(temp);
        }
    }



}
