# Automation Test with Selenium & TestNG

## Giới thiệu
Dự án này thực hiện kiểm thử tự động các chức năng trên trang web [Lotte Cinema](https://www.lottecinemavn.com) bằng **Selenium WebDriver** kết hợp với **TestNG**. Mục tiêu của dự án là kiểm tra các chức năng quan trọng như đăng nhập, đặt vé, xem lịch chiếu, và kiểm tra bảo mật.

## Cấu trúc thư mục
- `base/` : Chứa lớp cơ sở cung cấp các phương thức hỗ trợ chung.
- `tests/` : Chứa các bài kiểm thử chính.
- `security/` : Chứa các bài kiểm thử bảo mật.
- `utils/` : Chứa các tiện ích hỗ trợ kiểm thử.

## Mô tả các file
### 1. `BaseTest.java`
- Lớp cơ sở thiết lập trình duyệt Chrome, chờ đợi phần tử và xử lý quảng cáo.
- Hỗ trợ mở trang đăng nhập và đăng nhập vào hệ thống.

### 2. `BruteForceLogin.java`
- Thử đăng nhập nhiều lần bằng mật khẩu sai để kiểm tra khóa tài khoản.
- Kiểm tra đăng nhập với thông tin chính xác.

### 3. `PageTest.java`
- Kiểm tra điều hướng trang web.
- Kiểm tra tải lại trang.
- Kiểm tra tốc độ tải trang.

### 4. `Security.java`
- Kiểm tra lỗ hổng bảo mật **XSS Injection**.
- Kiểm tra **SQL Injection** qua URL.

### 5. `CustomerInfoTest.java`
- Kiểm tra lịch sử mua vé của người dùng.
- Kiểm tra lịch sử xem phim.
- Kiểm tra cập nhật thông tin tài khoản.

### 6. `LoginTest.java`
- Kiểm thử đăng nhập thành công.
- Kiểm thử đăng nhập với mật khẩu sai.
- Kiểm thử đăng nhập với email không hợp lệ.
- Kiểm thử đăng nhập với ô trống.
- Kiểm tra hiệu năng đăng nhập.

### 7. `MouseActions.java`
- Kiểm tra thao tác chuột như hover, click, double-click và click chuột phải.

### 8. `MovieScheduleTest.java`
- Kiểm tra lịch chiếu phim theo từng ngày.

### 9. `SearchFim.java`
- Kiểm tra tìm kiếm phim đang chiếu.
- Kiểm tra tìm kiếm phim sắp chiếu.

### 10. `ViewInforMovieTest.java`
- Kiểm tra xem thông tin phim bằng cách click vào tiêu đề hoặc nút "Chi Tiết".

### 11. `BookingTest.java`
- Kiểm tra đặt vé thành công.
- Kiểm tra chọn khu vực rạp, tên rạp, suất chiếu, ghế ngồi và thanh toán.

### 12. `ReplayIfFailed.java`
- Tự động thử lại kiểm thử thất bại tối đa 5 lần để đảm bảo kết quả đáng tin cậy.

## Công nghệ sử dụng
- **Java** với **Selenium WebDriver**.
- **TestNG** để quản lý kiểm thử.
- **WebDriverManager** để quản lý driver của trình duyệt.
- **Gradle** để quản lý dependencies và build dự án.

## Cách chạy kiểm thử
1. **Cài đặt Gradle** (nếu chưa có):
   ```bash
   sudo apt install gradle  # Linux
   choco install gradle     # Windows (Chocolatey)
   ```
2. **Chạy kiểm thử bằng Gradle**:
   ```bash
   gradle test
   ```
3. **Xem báo cáo TestNG**:
   - Mở file `build/reports/tests/test/index.html` trong trình duyệt.

## Lưu ý
- Cần cài đặt **Google Chrome** và **ChromeDriver** phù hợp với phiên bản Chrome trên máy.
- Kiểm tra kết nối internet trước khi chạy kiểm thử.

## Nhóm tác giả


