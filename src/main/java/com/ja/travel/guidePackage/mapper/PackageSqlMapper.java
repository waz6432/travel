package com.ja.travel.guidePackage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ja.travel.dto.GuidePlanningApplicationDto;
import com.ja.travel.dto.GuidePlanningDto;
import com.ja.travel.dto.PlanCityDto;
import com.ja.travel.dto.PlanDayDto;
import com.ja.travel.dto.PlanDto;
import com.ja.travel.dto.PlanPlaceDto;
import com.ja.travel.dto.UserDto;

public interface PackageSqlMapper {
	
	public GuidePlanningDto getPlanAndGuidePlanningInfoByPlanId(int plan_id);

	public int createGuidePlanningPK();

	public void insertGuidePlanningData(GuidePlanningDto guidePlanningDto);

	public void updateToRecruiting(GuidePlanningDto guidePlanningDto);

	public void updateToPlanning(GuidePlanningDto guidePlanningDto);

	public void updateGuidePlanningData(GuidePlanningDto guidePlanningDto);

	public void insertMyInfoWhenRecruting(GuidePlanningApplicationDto guidePlanningApplicationDto);

	public List<GuidePlanningDto> getGuidePlanningList(@Param("pageNum") int pageNum,
			@Param("searchType") String searchType,
			@Param("searchWord") String searchWord,
			@Param("user_id") int user_id);

	public int getUserIdFromGuidePlanning(GuidePlanningDto guidePlanning);

	public List<GuidePlanningApplicationDto> getGuidePlanningAcceptCountByGuidePlanningId(int guide_planning_id);

	public void acceptGuideApplicationaStatusByUser(GuidePlanningApplicationDto guidePlanningApplicationDto);

	public void refusalGuideApplicationaStatusByUser(GuidePlanningApplicationDto guidePlanningApplicationDto);

	public GuidePlanningApplicationDto getApplicationStatus(GuidePlanningApplicationDto guidePlanningApplicationDto);

	public GuidePlanningDto getGuidePlanningByGuidePlanningId(int guide_planning_id);

	public List<PlanDayDto> getPlanDay(int plan_id);

	public List<PlanPlaceDto> getPlaceByPlanId(PlanDayDto planDay);

	public PlanDto getPlanByGuidePlanningId(int guide_planning_id);

	public UserDto getUserByGuidePlanningId(int guide_planning_id);

	public List<PlanDayDto> getPlanDayByPlanId(int plan_id);

	public PlanCityDto getCityByPlace(int plan_city_id);

	

	
	
	

	

}