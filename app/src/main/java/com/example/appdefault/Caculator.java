package com.example.appdefault;

public class Caculator {
    public static double calculateBMI(double weight, double height) {
        // Chuyển đổi chiều cao từ cm sang mét
        double heightInMeter = height / 100.0;

        // Tính BMI bằng cách chia cân nặng (kg) cho bình phương chiều cao (m)
        double bmi = weight / (heightInMeter * heightInMeter);

        return bmi;
    }
    public double calculateDailyProteinRequirement(double weight, int activityLevel) {
        double proteinMultiplier = 0;

        // Xác định hệ số nhân dựa trên mức độ hoạt động
        switch (activityLevel) {
            case 1:
                proteinMultiplier = 0.8; // Ít hoạt động
                break;
            case 2:
                proteinMultiplier = 1.0; // Hoạt động nhẹ
                break;
            case 3:
                proteinMultiplier = 1.2; // Hoạt động trung bình
                break;
            case 4:
                proteinMultiplier = 1.6; // Hoạt động nặng
                break;
            case 5:
                proteinMultiplier = 2.0; // Rất năng động
                break;
            default:
                // Mặc định lấy mức hoạt động trung bình
                proteinMultiplier = 1.2;
                break;
        }

        // Tính lượng protein cần nạp mỗi ngày
        double dailyProteinRequirement = weight * proteinMultiplier;
        return dailyProteinRequirement;
    }


}
