<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="utf-8" />
    <title></title>
    <link rel="stylesheet" href="/resources/css/common/header.css" type="text/css">
    <link rel="stylesheet" href="/resources/css/admin/admin_main.css" type="text/css">
</head>

<body>
    <div id="container">
        <div class="header">
            <div class="top_menu">
                <span><a href="#" class="login-panel">로그인</a></span>
                <span class="header_divider">|</span>
                <span><a href="#" class="login-panel">회원가입</a></span>
                <span class="header_divider">|</span>
                <span><a href="#" class="login-panel">고객센터</a></span>
            </div>
            <div class="main-logo">
                <div class="home-logo"><img src="/resources/img/logo.png" width="230px" height="100px"></a></div>
            </div>
        </div>
        <div id="content">
            <!-- sidebar -->
            <div id="sidebar">
                <div id="admin-menu">
                    <h2>회원관리</h2>
                    <h2>상품관리</h2>
                    <h2>배너관리</h2>
                    <h2>판매요청관리</h2>
                    <h2>거래내역관리</h2>
                    <h2>포인트관리</h2>
                    <h2>통계분석</h2>
                </div>
                <div id="admin-memo">
                    <span class="span">Memo</span>
                    <textarea id="memo">2021-03-25 관리자 메모내용</textarea>
                </div>
            </div>
            <div id="main">
                <h2>관리자 메인</h2>
                <br>
                <h3 style="text-align:center">Status Board</h3>
                <br>
                <div>
                    <table id="status-board">
                        <tr>
                            <th>오늘 매출</th>
                            <th>신규회원</th>
                            <th>판매요청</th>
                            <th>1:1문의</th>
                            <th>방문자</th>
                        </tr>
                        <tr>
                            <td>1,023,810원</td>
                            <td>15명</td>
                            <td><a href="#">32건</a></td>
                            <td><a href="#">2건</a></td>
                            <td>648명</td>
                        </tr>
                    </table>
                </div>
                <br>
                <div id="svchart">
                    <img src="../img/chart2.png" width=49.5%>
                    <img src="../img/chart3.png" width=49.5%>
                </div>
                <br>
                <div id="piechart">
                    <img src="../img/chart4.png" width=100% height=300px>
                </div>
                <div class="menu-container">
                    <div>
                        <div>
                            <div class="menu-title" id="menu1">
                                <div>
                                    <span>1:1문의</span>
                                </div>
                                <div class="btn-wrapper">
                                    <input class="btn" type="button" onclick="location.href='#'" value="더보기"><br><br>
                                </div>
                            </div>
                        </div>
                        <p class="article">article</p><br>
                        <p class="article">article</p><br>
                        <p class="article">article</p><br>
                        <p class="article">article</p><br>
                        <p class="article">article</p><br>
                    </div>
                    <div>
                        <div>
                            <div class="menu-title" id="menu2">
                                <div>
                                    <span>판매요청</span>
                                </div>
                                <div class="btn-wrapper">
                                    <input class="btn" type="button" onclick="location.href='#'" value="더보기"><br><br>
                                </div>
                            </div>
                        </div>
                        <p class="article">article</p><br>
                        <p class="article">article</p><br>
                        <p class="article">article</p><br>
                        <p class="article">article</p><br>
                        <p class="article">article</p><br>
                    </div>
                    <div>
                        <div>
                            <div class="menu-title" id="menu3">
                                <div>
                                    <span>공지사항</span>
                                </div>
                                <div class="btn-wrapper">
                                    <input class="btn" type="button" onclick="location.href='#'" value="더보기"><br><br>
                                </div>
                            </div>
                        </div>
                        <p class="article">article</p><br>
                        <p class="article">article</p><br>
                        <p class="article">article</p><br>
                        <p class="article">article</p><br>
                        <p class="article">article</p><br>
                    </div>
                </div>
            </div>
        </div>

    </div>

</body>

</html>