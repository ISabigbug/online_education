function load2D_bg() {
    var background = document.getElementById("background");
    var range = 40;
    var calcValue = function calcValue(a, b) {
        return (a / b * range - range / 2).toFixed(1);
    };
    var timeout = void 0;
    document.addEventListener('mousemove',
        function (_ref) {
            var x = _ref.x,
                y = _ref.y;
            if (timeout) {
                window.cancelAnimationFrame(timeout);
            }
            timeout = window.requestAnimationFrame(function () {
                var yValue = calcValue(y, window.innerHeight);
                var xValue = calcValue(x, window.innerWidth);


                background.style.backgroundPositionX = xValue * 1 - 200 + "px ";
                background.style.backgroundPositionY = (-yValue * 0.75 - 50) + "px";
            })
        }, false);

}

function load2D_bg2() {
    var background = document.getElementById("background2");
    var range = 40;
    var calcValue = function calcValue(a, b) {
        return (a / b * range - range / 2).toFixed(1);
    };
    var timeout = void 0;
    document.addEventListener('mousemove',
        function (_ref) {
            var x = _ref.x,
                y = _ref.y;
            if (timeout) {
                window.cancelAnimationFrame(timeout);
            }
            timeout = window.requestAnimationFrame(function () {
                var yValue = calcValue(y, window.innerHeight);
                var xValue = calcValue(x, window.innerWidth);

                background.style.backgroundPositionX = xValue * 1 - 200 + "px ";
                background.style.backgroundPositionY = (-yValue * 0.75 - 50) + "px";
            })
        }, false);

}

function load2D_bg2_2() {
    var background = document.getElementById("background2");
    document.onmousemove = function () {
        console.log(window.outerHeight);
        background.style.opacity = 1 - (getMousePos() / window.outerHeight * 2.5);
    }
}

function getMousePos(event) {
    var e = event || window.event;
    var scrollY = document.documentElement.scrollTop || document.body.scrollTop;
    var y = e.pageY || e.clientY + scrollY;
    return y;
}

function load2D_lihui() {
    var background = document.getElementById("lihui");
    var range = 40;
    var calcValue = function calcValue(a, b) {
        return (a / b * range - range / 2).toFixed(1);
    };
    var timeout = void 0;
    document.addEventListener('mousemove',
        function (_ref) {
            var x = _ref.x,
                y = _ref.y;
            if (timeout) {
                window.cancelAnimationFrame(timeout);
            }
            timeout = window.requestAnimationFrame(function () {
                var xValue = calcValue(x, window.innerWidth);

                background.style.left = xValue * 0.7 - 100 + "px";

            })
        }, false);

}

function load2D_yinghua() {
    var background = document.getElementById("yinghua");
    var range = 40;
    var calcValue = function calcValue(a, b) {
        return (a / b * range - range / 2).toFixed(1);
    };
    var timeout = void 0;
    document.addEventListener('mousemove',
        function (_ref) {
            var x = _ref.x,
                y = _ref.y;
            if (timeout) {
                window.cancelAnimationFrame(timeout);
            }
            timeout = window.requestAnimationFrame(function () {
                var yValue = calcValue(y, window.innerHeight);
                var xValue = calcValue(x, window.innerWidth);
                background.style.backgroundPositionX = xValue * 1.5 - 200 + "px ";
                background.style.backgroundPositionY = (-yValue * 1 - 50) + "px";
            })
        }, false);

}
var i = 0;

function aboutMe() {
    var slogan = document.getElementById("slogan");
    var slogan2 = document.getElementById("slogan2");
    if (i == 0) {
        i = 1;
        slogan.style.height = "100px";
        slogan2.innerHTML = "包教会在线教育平台，该平台会定期推出教学视频，用户可以在线学习免费教育视频或购买付费视频进行学习，平台支持云视频点播等服务，为用户构建一个舒适的在线学习平台！";
    } else {
        i = 0;
        slogan.style.height = "50px";
        slogan2.innerHTML = "没有你学不会的技术，包教会！";
    }

}