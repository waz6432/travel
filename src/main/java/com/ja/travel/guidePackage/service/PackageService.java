package com.ja.travel.guidePackage.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ja.travel.dto.GuidePlanningApplicationDto;
import com.ja.travel.dto.GuidePlanningDto;
import com.ja.travel.dto.PlanCityDto;
import com.ja.travel.dto.PlanDayDto;
import com.ja.travel.dto.PlanDto;
import com.ja.travel.dto.PlanPlaceDto;
import com.ja.travel.dto.UserDto;
import com.ja.travel.guidePackage.mapper.PackageSqlMapper;
import com.ja.travel.login.mapper.LoginSqlMapper;
import com.ja.travel.plan.mapper.PlanSqlMapper;

@Service
public class PackageService {

	@Autowired
	private PackageSqlMapper packageSqlMapper;

	@Autowired
	private LoginSqlMapper loginSqlMapper;
	@Autowired
	private PlanSqlMapper planSqlMapper;

	public void isAlreadyPlanning(String action, GuidePlanningDto guidePlanningDto, HttpSession session) {

		UserDto sessionuser = (UserDto) session.getAttribute("sessionuser");
		GuidePlanningApplicationDto guidePlanningApplicationDto = new GuidePlanningApplicationDto();
		GuidePlanningDto check = packageSqlMapper.getPlanAndGuidePlanningInfoByPlanId(guidePlanningDto.getPlan_id());

		if (check == null) {
			int pk = packageSqlMapper.createGuidePlanningPK();
			guidePlanningDto.setGuide_planning_id(pk);

			if (action.equals("recruit")) {
				guidePlanningApplicationDto.setUser_id(sessionuser.getUser_id());
				guidePlanningApplicationDto.setGuide_planning_id(pk);

				packageSqlMapper.insertGuidePlanningData(guidePlanningDto); // planning insert
				packageSqlMapper.insertMyInfoWhenRecruting(guidePlanningApplicationDto);// 참가방 하나 파놓기(방장)
				packageSqlMapper.updateToRecruiting(guidePlanningDto); // plan 모집중으로 바꾸기
			} else {
				packageSqlMapper.insertGuidePlanningData(guidePlanningDto);
				packageSqlMapper.updateToPlanning(guidePlanningDto);
			}
		} else {
			guidePlanningDto.setGuide_planning_id(check.getGuide_planning_id());

			if (action.equals("recruit")) {
				guidePlanningApplicationDto.setGuide_planning_id(guidePlanningDto.getGuide_planning_id());
				guidePlanningApplicationDto.setUser_id(sessionuser.getUser_id());

				packageSqlMapper.updateGuidePlanningData(guidePlanningDto);
				packageSqlMapper.updateToRecruiting(guidePlanningDto);
				packageSqlMapper.insertMyInfoWhenRecruting(guidePlanningApplicationDto);
			} else {
				packageSqlMapper.updateGuidePlanningData(guidePlanningDto);
				packageSqlMapper.updateToPlanning(guidePlanningDto);
			}
		}
	}

	public List<Map<String, Object>> getGuidePlanningList(int pageNum, String searchType, String searchWord,
			HttpSession session) {

		UserDto user = (UserDto) session.getAttribute("sessionuser");
		int user_id = 0;

		if (user != null) {
			user_id = user.getUser_id();
		}

		List<GuidePlanningDto> guidePlanningList = packageSqlMapper.getGuidePlanningList(pageNum, searchType,
				searchWord, user_id);
		List<Map<String, Object>> list = new ArrayList<>();

		for (GuidePlanningDto guidePlanning : guidePlanningList) {

			Map<String, Object> map = new HashMap<>();

			int userId = packageSqlMapper.getUserIdFromGuidePlanning(guidePlanning);

			UserDto userOfPlanning = loginSqlMapper.selectById(userId);
			List<GuidePlanningApplicationDto> guidePlanningAppList = packageSqlMapper
					.getGuidePlanningAcceptCountByGuidePlanningId(guidePlanning.getGuide_planning_id());
			PlanDto plan = planSqlMapper.selectById(guidePlanning.getPlan_id());
			List<PlanDayDto> planDayList = planSqlMapper.getPlanDayByplan_id(plan);

			map.put("user", userOfPlanning);
			map.put("guidePlanning", guidePlanning);
			map.put("guidePlanningAcceptCount", guidePlanningAppList);
			map.put("plan", plan);
			map.put("planDay", planDayList.size());

			list.add(map);
		}

		return list;

	}

	public int getGuidePlanningCount(String searchWord) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Map<String, Object> updateGuideApplicationaStatusByUser(
			GuidePlanningApplicationDto guidePlanningApplicationDto, String value) {

		if (value.equals("수락")) {
			packageSqlMapper.acceptGuideApplicationaStatusByUser(guidePlanningApplicationDto);
		} else {
			packageSqlMapper.refusalGuideApplicationaStatusByUser(guidePlanningApplicationDto);
		}

		GuidePlanningApplicationDto planningStatus = packageSqlMapper.getApplicationStatus(guidePlanningApplicationDto);
		GuidePlanningDto guidePlanning = packageSqlMapper
				.getGuidePlanningByGuidePlanningId(planningStatus.getGuide_planning_id());
		List<GuidePlanningApplicationDto> count = packageSqlMapper
				.getGuidePlanningAcceptCountByGuidePlanningId(guidePlanning.getGuide_planning_id());

		Map<String, Object> map = new HashMap<>();
		map.put("planningStatus", planningStatus);
		map.put("guidePlanning", guidePlanning);
		map.put("guidePlanningAcceptCount", count);

		return map;
	}

	public Map<String, Object> getPlaceByDayForPlan(int guide_planning_id) {

		PlanDto plan = packageSqlMapper.getPlanByGuidePlanningId(guide_planning_id);
		UserDto user = packageSqlMapper.getUserByGuidePlanningId(guide_planning_id);
		List<PlanDayDto> planDayList = packageSqlMapper.getPlanDayByPlanId(plan.getPlan_id());
		GuidePlanningDto guidePlanningDto = packageSqlMapper.getGuidePlanningByGuidePlanningId(guide_planning_id);
		int planDayListSize = planDayList.size();

		List<Map<String, Object>> list = new ArrayList<>();
		
		for (PlanDayDto planDay : planDayList) {
			Map<String, Object> map = new HashMap<String, Object>();
			List<PlanPlaceDto> placeList = packageSqlMapper.getPlaceByPlanId(planDay);
			List<Map<String, Object>> placeListWithCity = new ArrayList<>();
			
			for (PlanPlaceDto planPlaceDto : placeList) {
				
				Map<String, Object> placeMap = new HashMap<String, Object>();
				PlanCityDto planCityDto = packageSqlMapper.getCityByPlace(planPlaceDto.getPlan_city_id());
				String planCityName = planCityDto.getPlan_city_name();
				placeMap.put("planPlace", planPlaceDto);
				placeMap.put("planCityName", planCityName);
				placeListWithCity.add(placeMap);
			}
			map.put("placeList", placeListWithCity);
			map.put("planDay", planDay);
			list.add(map);
			

		}

		Map<String, Object> resultMap = new HashMap<>();

		resultMap.put("user", user);
		resultMap.put("planDayListSize", planDayListSize);
		resultMap.put("guidePlanningDto", guidePlanningDto);
		resultMap.put("list", list);

		return resultMap;

	}

}