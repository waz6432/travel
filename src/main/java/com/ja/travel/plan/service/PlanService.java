package com.ja.travel.plan.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ja.travel.dto.PlanCityDto;
import com.ja.travel.dto.PlanDayCityDto;
import com.ja.travel.dto.PlanDayDto;
import com.ja.travel.dto.PlanDto;
import com.ja.travel.dto.PlanPlaceDto;
import com.ja.travel.dto.PlanRouteCityDto;
import com.ja.travel.dto.UserDto;
import com.ja.travel.login.mapper.LoginSqlMapper;
import com.ja.travel.plan.mapper.PlanSqlMapper;

@Service
public class PlanService {
	
//	private int lastRegisteredPlanId = 0;
//	private int lastRegisteredPlanDay = 0;

	@Autowired
	public PlanSqlMapper planSqlMapper;

	@Autowired
	public LoginSqlMapper loginSqlMapper;

	public void registerPlan(PlanDto planDto) {
	    System.out.println("플랜 입력 서비스 작동");

	    System.out.println(planDto);

	    int planPk = planSqlMapper.createPlanPk();
	    planDto.setPlan_id(planPk);

	    planSqlMapper.planInsert(planDto);

	    // 플랜 등록 후 lastRegisteredPlanId 갱신
//	    lastRegisteredPlanId = planDto.getPlan_id();
//	    lastRegisteredPlanDay = 0;
	}

	public Map<String, Object> registerPlanDay(PlanDayDto planDayDto) {
	   
		
		 Map<String, Object> map = new HashMap<String, Object>();

		    int maxPlanDay = planSqlMapper.getMaxPlanDay(planDayDto.getPlan_id());
		    
		    int planDayPk = planSqlMapper.createPlanDayPk();
		    planDayDto.setPlan_day_id(planDayPk);
		    
		    int plan_day = maxPlanDay + 1;
		    
		    planDayDto.setPlan_day(plan_day);
	    	planSqlMapper.planDayInsert(planDayDto);
	    	
	    	map.put("planDayDto", planDayDto);
	    	map.put("day", plan_day);
	    	
	    	return map;
	    
	    
	}
	
	public void copyPlanInsert(PlanDto planDto) {
		
//		System.out.println("플랜 참조하기 서비스 작동");
		
		System.out.println(planDto);
		
		int planPk = planSqlMapper.createPlanPk();
		planDto.setPlan_id(planPk);
		
		planSqlMapper.copyPlanInsert(planDto);
    }
	
	public List<PlanDayDto> getPlanDayList(int plan_id){
		
		
		
		List<PlanDayDto> planDayDtoList = planSqlMapper.planDaySelect(plan_id);
		
	
		

		return planDayDtoList;
	}
	
	public List<Map<String, Object>> getPlanList(){
		
//		System.out.println("플랜 리스트 출력 서비스 작동");
		
		List<Map<String, Object>> list = new ArrayList<>();
		
		List<PlanDto> planDtoList = planSqlMapper.planSelectAll();
		
		for(PlanDto planDto : planDtoList) {
			
			int user_id = planDto.getUser_id();
			
			UserDto userDto = loginSqlMapper.selectById(user_id);
			
			Map<String, Object> map = new HashMap<>();
			
			map.put("userDto", userDto);
			map.put("planDto", planDto);
			
			if (planDto.getReferenced_plan_id() != 0) {
	            int referencedPlanId = planDto.getReferenced_plan_id();
	            PlanDto referencedPlanDto = planSqlMapper.selectById(referencedPlanId);
	            map.put("referencedPlanDto", referencedPlanDto);
	        }
			
			list.add(map);
		}
		return list;
	}
	
	public Map<String, Object> getPlan(int plan_id){
		
//		System.out.println("플랜 상세보기 서비스 작동");
		
		Map<String, Object> map = new HashMap<>();
		
		PlanDto planDto = planSqlMapper.selectById(plan_id);
		UserDto userDto = loginSqlMapper.selectById(planDto.getUser_id());
		
		map.put("userDto", userDto);
		map.put("planDto", planDto);
		
		return map;
	}
	
	// 플래너 삭제
	public void deleteContent(int id) {
		planSqlMapper.deleteById(id);
	}
	
	// 플래너 수정
	public void updateContent(PlanDto planDto) {
		planSqlMapper.update(planDto);
	}
	
	// 플래너 공개여부 수정
	public void updateDisclosure(int planId, String planDisclosureStatus) {
	    planSqlMapper.updateDisclosureStatus(planId, planDisclosureStatus);
	}
	
	// 지역 목록
	public List<PlanCityDto> loadCityList(String searchType, String searchWord){
				
		List<PlanCityDto> cityList =  planSqlMapper.loadCityList(searchType, searchWord);
		
		return cityList;
	}
	
	
	
	// 지역별 명소 목록
	public List<PlanPlaceDto> getPlanPlaceListByCity(int plan_city_id){	
		
		return planSqlMapper.planPlaceByPlanCityId(plan_city_id); 
	}
	
	// 플랜 날짜별 지역 입력
	public void registerPlanDayCity(PlanDayCityDto planDayCityDto){
		
		System.out.println("플랜 일정별 도시 입력 서비스 작동");
		
		int planDayCityPk = planSqlMapper.createPlanDayCityPk();
		
		planDayCityDto.setPlan_day_city_id(planDayCityPk);
		
		planSqlMapper.planDayCityInsert(planDayCityDto);
	}
	
	// 플랜 날짜별 지역 리스트
	public List<Map<String, Object>> getPlanDayCityList(int plan_id){
		
		List<Map<String, Object>> list = new ArrayList<>();
		
		List<PlanDayDto> planDayDtoList = planSqlMapper.planDaySelectByplan_id(plan_id);
		
		for(PlanDayDto planDayDto : planDayDtoList) {
			
			int plan_day_id = planDayDto.getPlan_day_id();
			
			List<PlanDayCityDto> planDayCityDtoList = planSqlMapper.planDayCitySelect(plan_day_id);
			
			for(PlanDayCityDto planDayCityDto : planDayCityDtoList) {
				
				System.out.println(planDayCityDto);
				
				int plan_city_id = planDayCityDto.getPlan_city_id();
				
//				planDayCityDto.setPlan_day_id(plan_day_id);
				
				PlanCityDto planCityDto = planSqlMapper.planCityByPlanCityId(plan_city_id);
				
					Map<String, Object> map = new HashMap<>();
										
					map.put("planCityDto", planCityDto);
					map.put("planDayCityDto", planDayCityDto);
					map.put("planDayDto", planDayDto);
					
					list.add(map);
			}
		}
		return list;
	}

	public void registerNewPlanDay(PlanDayDto planDayDto) {
		
		 int planDayPk = planSqlMapper.createPlanDayPk();
		 planDayDto.setPlan_day_id(planDayPk);
		
		planSqlMapper.newPlanDayInsert(planDayDto);
		
	}
	
	//  지역별 명소 리스트
	public List<Map<String, Object>> getPlaceByCity(int plan_id) {
		List<Map<String, Object>> list = new ArrayList<>();

		List<PlanDayDto> planDayDtoList = planSqlMapper.planDaySelectByplan_id(plan_id);

		for (PlanDayDto planDayDto : planDayDtoList) {

			int plan_day_id = planDayDto.getPlan_day_id();

			List<PlanDayCityDto> planDayCityDtoList = planSqlMapper.planDayCitySelect(plan_day_id);

			for (PlanDayCityDto planDayCityDto : planDayCityDtoList) {

				System.out.println(planDayCityDto);

				int plan_city_id = planDayCityDto.getPlan_city_id();

				List<PlanPlaceDto> planPlaceDtoList = planSqlMapper.planPlaceByPlanCityId(plan_city_id);
				for(PlanPlaceDto planPlaceDto : planPlaceDtoList) {
					
					Map<String, Object> map = new HashMap<>();
					map.put("planPlaceDto", planPlaceDto);
					map.put("planDayCityDto", planDayCityDto);
					list.add(map);
				}
			}
		}
		return list;
	}

	public void registerPlanRouteCity(PlanRouteCityDto params) {
		
		int planRouteCityPk = planSqlMapper.createPlanRouteCityPk();
		params.setPlan_route_city_id(planRouteCityPk);
		
		planSqlMapper.planRouteCityInsert(params);
	}

	public List<Map<String, Object>> getRoutePlaceList(int plan_id) {
		
		List<Map<String, Object>> list = new ArrayList<>();

		List<PlanDayDto> planDayDtoList = planSqlMapper.planDaySelectByplan_id(plan_id);

		for (PlanDayDto planDayDto : planDayDtoList) {

			int plan_day_id = planDayDto.getPlan_day_id();

			List<PlanDayCityDto> planDayCityDtoList = planSqlMapper.planDayCitySelect(plan_day_id);

			for (PlanDayCityDto planDayCityDto : planDayCityDtoList) {

				System.out.println(planDayCityDto);
				
				int plan_day_city_id = planDayCityDto.getPlan_day_city_id(); 
				
				List<PlanRouteCityDto> planRouteCityDtoList = planSqlMapper.planRouteByPlanDayCityId(plan_day_city_id);
				
				for(PlanRouteCityDto planRouteCityDto : planRouteCityDtoList) {
					
//					int plan_city_id = planDayCityDto.getPlan_city_id();
					
					int plan_place_id = planRouteCityDto.getPlan_place_id();
					
					PlanPlaceDto planPlaceDto = planSqlMapper.planPlaceByPlanPlaceId(plan_place_id);
					Map<String, Object> map = new HashMap<>();
					map.put("planPlaceDto", planPlaceDto);
					map.put("planRouteCityDto", planRouteCityDto);
					map.put("planDayCityDto", planDayCityDto);
					list.add(map);
				}
				
//				List<PlanPlaceDto> planPlaceDtoList = planSqlMapper.planPlaceByPlanCityId(plan_city_id);
//				for(PlanPlaceDto planPlaceDto : planPlaceDtoList) {
//					
//				}
			}
		}
		return list;

	}

	public int guideCheck(int user_id) {
		
		Boolean result = planSqlMapper.selectGuideByuser_id(user_id);

		if(result == null) {
			
			return 0;
		} 
		
		return 1;
		
		
	}

	public int getGuide_idByuser_id(int user_id) {
		
		return planSqlMapper.getGuide_idByuser_id(user_id);
	}
	
	

	public List<PlanPlaceDto> getPlanPlaceList() {
		// TODO Auto-generated method stub
	
		
		List<PlanPlaceDto> placeList = planSqlMapper.getPlaceList();
		
		
		
		return placeList;
	}

	public List<PlanCityDto> getPlanCityList() {
		
		List<PlanCityDto> list = planSqlMapper.getAllCity();
		
		return list;
	}
	
	
	
	
	
	
	
	public List<PlanDayDto> getPlanDayList2(int plan_id){
		return planSqlMapper.planDaySelect(plan_id);
	}

	public List<PlanCityDto> getPlanCityList2(String word1, String word2){
		return planSqlMapper.planCitySelect2(word1, word2);
	}
	
	public List<PlanPlaceDto> getPlanPlaceList2(int cityId, String word){
		
		return planSqlMapper.planPlaceSelectAll2(cityId, word);
	}
	
	
	public List<Map<String, Object>> getggg(int dayId) {
		List<Map<String, Object>> list = new ArrayList<>();
		
		List<PlanDayCityDto> planDayCityDtoList = planSqlMapper.get1(dayId);
		
		for(PlanDayCityDto qqq : planDayCityDtoList) {
			
			Map<String, Object> map = new HashMap<String, Object>();
			
			PlanCityDto planCityDto = planSqlMapper.get3(qqq.getPlan_city_id());
			
			
			map.put("planDayCityDto", qqq);
			map.put("planCityDto", planCityDto);
			
			List<PlanRouteCityDto> planRouteCityDtoList = planSqlMapper.get2(qqq.getPlan_day_city_id());
			
			List<Map<String, Object>> listInner = new ArrayList<>();
			
			for(PlanRouteCityDto planRouteCityDto : planRouteCityDtoList) {
				Map<String, Object> mapInner = new HashMap<String, Object>();
				
				PlanPlaceDto planPlaceDto = planSqlMapper.get4(planRouteCityDto.getPlan_place_id());
				
				mapInner.put("planRouteCityDto", planRouteCityDto);
				mapInner.put("planPlaceDto", planPlaceDto);
				
				listInner.add(mapInner);
			}
			
			map.put("listInner", listInner);
		
			list.add(map);
		}
		
		
		
		
		return list;
	}

	// 일정별 명소 선택시 동시 입력 서비스 시작
	@Transactional
	public int registerPlace(int planDayId, int planCityId, int planPlaceId) {

		int aaa = 0;

		// 존재 여부
		PlanDayCityDto planDayCityDto = planSqlMapper.getPlanDayCityByCityIdAndDayId(planDayId, planCityId);

		if (planDayCityDto != null) {
			aaa = planDayCityDto.getPlan_day_city_id();
		} else {
			planSqlMapper.insertPlanDayCity(planDayId, planCityId);
			PlanDayCityDto planDayCityDto2 = planSqlMapper.getPlanDayCityByCityIdAndDayId(planDayId, planCityId);
			aaa = planDayCityDto2.getPlan_day_city_id();
		}

//	    int lastInsertedId = getLastInsertedId("plan_day_city", "plan_day_city_id");
		planSqlMapper.insertPlanRouteCity(planPlaceId, aaa);
		// plan_day_city_id 값을 반환
		return aaa;
	}
	// 일정별 명소 선택시 동시 입력 서비스 끝
	
	// plan 검색
	public List<Map<String, Object>> searchPlan(String type, String word){
		
		List<Map<String, Object>> list = new ArrayList<>();
		
		List<PlanDto> planDtoList = planSqlMapper.searchPlan(type, word);
		
		for(PlanDto planDto : planDtoList) {
			
			int user_id = planDto.getUser_id();
			
			UserDto userDto = loginSqlMapper.selectById(user_id);
			
			Map<String, Object> map = new HashMap<>();
			
			map.put("userDto", userDto);
			map.put("planDto", planDto);
			
			list.add(map);
		}
		
	    return list; 
	}

	   public List<PlanPlaceDto> getAllPlaceList2() {
		      
	      return planSqlMapper.getAllPlace2();
	   }


	

	
	
	



	

	
}