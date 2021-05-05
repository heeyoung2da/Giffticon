// 관심상품
let wishListService = (function () {

    let header = $("meta[name='_csrf_header']").attr("content");
    let token = $("meta[name='_csrf']").attr("content");

    // 관심상품 추가
    function add(wish, callback, error) {
        console.log("insert wish....")

        $.ajax({

            type: "POST",
            url: "/wishList/register",
            beforeSend: function (xhr){
                xhr.setRequestHeader( header, token );
            },
            data: JSON.stringify(wish),
            contentType: "application/json; charset=utf-8",
            success: function (result) {
                if (callback) {
                    callback(result);
                }
            }, error: function (xhr, status, er) {
                if (error) {
                    error(er);
                    alert("다시 시도해주세요.");
                }
            }
        })
    }

    // 관심상품 삭제
    function remove(wish, callback, error) {
        console.log("delete wish....")

        $.ajax({
            type: "POST",
            url: "/wishList/remove",
            beforeSend: function (xhr){
                xhr.setRequestHeader( header, token );
            },
            data: JSON.stringify(wish),
            contentType: "application/json; charset=utf-8",
            success: function (result) {
                if (callback) {
                    callback(result);
                }
            }, error: function (xhr, status, er) {
                if (error) {
                    error(er);
                    alert("다시 시도해주세요.");
                }
            }
        })
    }

    return {
        add: add,
        remove: remove
    };
})();