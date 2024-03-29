package likelion.univ.domain.recruit.service;

import likelion.univ.domain.recruit.adopter.RecruitAdopter;
import likelion.univ.domain.recruit.entity.Recruit;
import likelion.univ.domain.recruit.repository.RecruitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecruitQueryService {

    private final RecruitAdopter recruitAdopter;

    public List<Recruit> queryAllByUniversityName(String universityName) {
        return recruitAdopter.findAllByUniversityId(universityName);
    }
}
