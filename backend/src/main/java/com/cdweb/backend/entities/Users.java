package com.cdweb.backend.entities;

import com.cdweb.backend.common.Constant;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Users extends BaseEntity{
    private String username;
    private String password;
    private String fullName;
    private String gmail;
    private String otpCode;
    private Date otpRequestedTime;
    @Column(name = "is_active")
    private boolean isActive;
    @ManyToOne
    @JoinColumn(name = "roles_id", nullable = false)
    private Roles roles;

    public boolean isOTPRequired() {
        if (this.getOtpCode() == null) {
            return false;
        }

        long currentTimeInMillis = System.currentTimeMillis();
        long otpRequestedTimeInMillis = this.otpRequestedTime.getTime();

        if (otpRequestedTimeInMillis + Constant.OTP_VALID_DURATION < currentTimeInMillis) {
            // OTP expires
            return false;
        }

        return true;
    }
}
