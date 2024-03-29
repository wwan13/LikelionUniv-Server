package likelion.univ.alarm.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion.univ.alarm.dto.AlarmContentsDto;
import likelion.univ.alarm.dto.UserListDto;
import likelion.univ.alarm.usecase.AlarmUsecase;
import likelion.univ.alarm.usecase.EmailAlarmUsecase;
import likelion.univ.alarm.controller.enums.AlarmType;
import likelion.univ.alarm.usecase.GetUsersUsecase;
import likelion.univ.domain.user.entity.User;
import likelion.univ.domain.user.repository.searchcondition.UserSearchCondition;
import likelion.univ.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "Alarm", description = "알람을 보내는 API")
@RestController
@RequestMapping(value = "/v1/alarm", produces = "application/json")
@RequiredArgsConstructor
public class AlarmController {

    private final Map<String, AlarmUsecase> alarmUsecaseMap;
    private final GetUsersUsecase getUsersUsecase;

    @PostMapping
    @Operation(
            summary = "알람 전송 API",
            description = "Email, SMS 등 알람을 보내는 API 입니다. 아래 타입별로 다른 parameter 를 담아 요청을 보내주세요! \n"
                    + "EMAIL(이메일 알람) : content(메일 본문), emails(수신인), sender(송신인 이름), subject(메일 제목)\n"
                    + "SMS(문자메세지 알람) : content(메세지 본문), emails(수신인)\n"
                    + "RECRUIT_EMAIL(리크루팅 이메일 알람) : content(메일 본문), sender(송신인 이름), subject(메일 제목), universityName(대학 이름)\n"
                    + "RECRUIT_SMS(리크루팅 문자메세지 알람) : content(메세지 본문), universityName(대학 이름)")
    public SuccessResponse<String> emailAlarm(
            @RequestBody AlarmContentsDto alarmContentsDto,
            @RequestParam String type) {

        AlarmUsecase alarmUsecase = alarmUsecaseMap.get(AlarmType.of(type));
        alarmUsecase.execute(alarmContentsDto);

        return SuccessResponse.of("알람 전송 성공");
    }

    @GetMapping("/user")
    @Operation(
            summary = "유저 정보 요청 API",
            description = "알람 전송 전 어떤 유저에게 전송할지 선택하기 위해 모든 유저를 불러오는 API 입니다.\n"
                    + "유저 이름, 대학 이름, 파트 별로 필터링 가능 하며 전체 결과를 원할시 빈 문자열 혹은 파라미터를 생략하시면 됩니다.")
    public SuccessResponse<UserListDto> getUsers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String university,
            @RequestParam(required = false) String part) {
        UserSearchCondition condition = new UserSearchCondition(name, university, part);
        UserListDto users = getUsersUsecase.execute(condition);

        return SuccessResponse.of(users);
    }
}
