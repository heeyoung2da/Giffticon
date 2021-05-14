<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" href="/resources/css/user/mypage/mypage_info_editPwd.css" type="text/css">
<link rel="stylesheet" href="/resources/css/user/mypage/mypage_info_edit_account.css" type="text/css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<style>
    @import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap');
</style>
</head>
<body>
<div id="acc_register-content">
    <h2>계좌정보 수정</h2>

    <div id="acc_content">

        <div class="acc_info_section">
            <div>
                <h3>은행명</h3>
                <select id="acc-bankSelect">
                    <c:forEach items="${bnkList}" var="list">
                        <option value="<c:out value="${list.code}"/>">
                                ${list.codeName}
                        </option>
                    </c:forEach>
                </select>
            </div>

        </div>
        <div class="acc_info_section">
            <div>
                <h3>예금주</h3>
                <div class="input_text">
                    <input type="text" class="acc-holder" placeholder="기존 예금주 출력">
                </div>
            </div>

        </div>
        <div class="acc_info_section">
            <div>
                <h3>계좌번호</h3>
                <div class="input_text">
                    <input type="text" class="acc-account" placeholder="기존 계좌번호 출력">
                </div>
                <button class="btn btn-submit" id="btn-accConfirm">인증</button>
            </div>
            <div class="message" id="msg-accConfirm"></div>
        </div>
        <div class="acc_info_section">
            <div>
                <h3>비밀번호 입력</h3>
                <div class="input_text">
                    <input type="password" class="acc-originPwd" placeholder="">
                </div>
                <button class="btn btn-submit" id="btn-accPwdConfirm">인증</button>
            </div>
            <div class="message" id="msg-accPwd"></div>
        </div>

    </div>

    <div id="reg-btn-area">
        <button class="btn btn-active" id="modifyAcc">등록</button>
        <button class="btn btn-dark cancel" id="cancelAcc">취소</button>
    </div>
</div>
</body>
</html>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.24.0/moment.min.js"></script>
<script type="text/javascript" src="/resources/js/banking/openBanking.js"></script>
<script type="text/javascript">

    var checkAllConfirmed = [false, false];

    $(document).ready(function () {

        let csrfHeaderName = "${_csrf.headerName}";
        let csrfTokenValue = "${_csrf.token}";

        let oriEmail = "<c:out value="${user.email}"/>";
        let birth = "<c:out value="${user.birthDt}"/>";

        let bnkSelected;

        let holder = $(".acc-holder");
        let account = $(".acc-account");

        //계좌 인증 후 return 받을 값
        let isAuthed;
        let auth_dt;

        let accPwdConfirm = $("#btn-accPwdConfirm");
        let accOriPwd = $(".acc-originPwd");
        let accPwdMsg = $("#msg-accPwd");
        let accMsg = $("#msg-accConfirm");

        let accInfo = {};

        //selectBox의 은행명 가져오기
        $("#acc-bankSelect").change(function () {
            checkAllConfirmed[1] = false;
            accMsg.html("");
            bnkSelected = $(this).val();
        })

        holder.keyup(function(e){
            checkAllConfirmed[1] = false;
            accMsg.html("");
        })

        account.keyup(function(e){
            checkAllConfirmed[1] = false;
            accMsg.html("");
        })

        //기존 비밀번호 인증
        accPwdConfirm.on("click", function (e) {

            let msg = "";
            let oriPwdVal = accOriPwd.val();

            let checkUser = {
                email: oriEmail,
                pwd: oriPwdVal
            }

            $.ajax({
                url: '/user/mypage/checkPassword',
                contentType: "application/json",
                data: JSON.stringify(checkUser),
                type: 'post',
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
                },
                success: function () {
                    msg = "비밀번호가 일치합니다.";
                    checkIsCorrect(accPwdMsg, msg, true, 0);
                    accOriPwd.attr("readonly", true);
                },
                error: function (error) {

                    if(error.status == 406){
                        msg = "최초비밀번호를 등록 후 인증해주세요";
                    }else {
                        msg = "비밀번호가 다릅니다";
                    }
                    checkIsCorrect(accPwdMsg, msg, false, 0);
                }
            })
        })

        //계좌 인증 버튼
        $("#btn-accConfirm").on("click", function () {

            let holderVal = holder.val();
            let accountVal = account.val();

            //실명조회할 계좌 정보
            accInfo = {
                bnkCode: bnkSelected,
                holder: holderVal,
                acc: accountVal,
                birth: birth
            }

            //promise_ 토큰 획득 후 계좌실명조회
            getBankingAccTkn()
                .then((accTkn) => inqRealNameBnkAcc(accTkn, accInfo))
                .then((msg) => checkIsCorrect(accMsg, msg, true, 1))
                .catch((error) => checkIsCorrect(accMsg, error, false, 1))

        })


        $("#modifyAcc").on("click", function () {

            let originAcc = "<c:out value="${user.acc}"/>";

            if (checkAllConfirmed[0] == true && checkAllConfirmed[1] == true) {

                delete accInfo.birth;
                accInfo.userId = userId;

                let ajaxTo;

                //인증 api 설정 후 인증 시각도 같이 추가해서 보내기 (accRegister, accUpdate mapper도 now() 삭제)

                //최초 계좌 등록인 경우
                if (originAcc == null || originAcc == "") {
                    if (!confirm("계좌를 등록하시겠습니까?")) { return; }
                    ajaxTo = '/user/mypage/accRegister'
                } else { //계좌 수정의 경우
                    if (!confirm("등록계좌를 수정하시겠습니까? 기존 등록된 계좌는 삭제됩니다.")) { return; }
                    ajaxTo = '/user/mypage/accUpdate'
                }

                $.ajax({
                    url: ajaxTo,
                    method: 'post',
                    data: JSON.stringify(accInfo),
                    contentType: "application/json",
                    beforeSend: function (xhr) {
                        xhr.setRequestHeader(csrfHeaderName, csrfTokenValue);
                    },
                    success: function () {
                        alert("계좌가 등록되었습니다.");
                        window.location.reload();
                    },
                    error: function () {
                        alert("계좌 등록 실패. 관리자에게 문의하세요.");
                    }
                })

            } else { //정보 입력 다 안했을때
                alert("인증을 완료해주세요.");
                return;
            }
        })

        $("#cancelAcc").on("click", function () {

            $("#acc-bankSelect").val("선택").prop("selected", true);
            holder.val("");
            account.val("");
            accOriPwd.val("");
            accMsg.html("");
            accPwdMsg.html("");
            checkAllConfirmed = [false, false];
        })
    })
</script>
<script>
    function checkIsCorrect(div, msg, isTrue, i) {

        let str = "";
        checkAllConfirmed[i] = isTrue

        if (isTrue) {
            str += "<i class='far fa-check-circle'></i>";

        } else {
            str += "<i class='fas fa-exclamation-circle'></i>";
        }

        str += "<p>&nbsp;" + msg + "</p>";
        div.html(str);
    }
</script>