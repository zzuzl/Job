package com.zzu.daoback;

import com.zzu.model.*;
import org.apache.ibatis.session.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/8.
 */
//@Component
public class JobDao {
	@Resource
	private SqlSession session;

	public List<Classify> getAllClassifies() {
		List<Classify> classifies = null;
		classifies = session.selectList("mapping.JobMapper.getAllClassifies");
		return classifies;
	}

	public List<Job> getAllCompanyJobs(int post_company) {
		List<Job> jobs = null;
		jobs = session.selectList("mapping.JobMapper.getAllCompanyJobs", post_company);
		return jobs;
	}

	public Job getJobById(int id) {
		Job job = null;
		job = session.selectOne("mapping.JobMapper.getJobById", id);
		return job;
	}

	public List<Resume> searchResume(int grade, int spare_time, String salary, int school, int page,String filter,boolean push) {
		List<Resume> resumes = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("grade", grade);
		map.put("spare_time", spare_time);
		map.put("salary", salary);
		map.put("school", school);
		map.put("page", page);
		map.put("start", (page - 1) * Common.COUNT);
		map.put("count", Common.COUNT);
		map.put("filter",filter);
		if(push) {
			map.put("push",1);
		} else {
			map.put("push",0);
		}

		resumes = session.selectList("mapping.ResumeMapper.searchResume", map);

		return resumes;
	}

	public List<School> getSchools() {

		List<School> schools = session.selectList("mapping.JobMapper.getSchools");
		return schools;
	}

	public Resume getResumeById(int id) {
		Resume resume = session.selectOne("mapping.ResumeMapper.getResumeById", id);
		return resume;
	}

	public void addSchool(String school) {
		session.insert("mapping.JobMapper.addSchool", school);
	}

	public void addMajors(List<String> majors) {
		session.insert("mapping.JobMapper.addMajors", majors);
	}

	public List<Job> getRecentJobs(int num) {
		List<Job> jobs = session.selectList("mapping.JobMapper.getRecentJobs", num);
		return jobs;
	}

	public List<Comment> getComments(int id) {
		List<Comment> comments = session.selectList("mapping.JobMapper.getComments", id);
		return comments;
	}

	public Collection getCollection(int u_id, int j_id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("u_id", u_id);
		map.put("j_id", j_id);
		Collection collection = session.selectOne("mapping.JobMapper.getCollection", map);
		return collection;
	}

	public void deleteCollection(int u_id, int j_id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("u_id", u_id);
		map.put("j_id", j_id);
		session.delete("mapping.JobMapper.deleteCollection", map);
	}

	public void addCollection(int u_id, int j_id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("u_id", u_id);
		map.put("j_id", j_id);
		session.insert("mapping.JobMapper.addCollection", map);
	}

	public List<Apply> getApplies(int u_id, int j_id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("u_id", u_id);
		map.put("j_id", j_id);

		List<Apply> applies = session.selectList("mapping.JobMapper.getApplies", map);
		return applies;
	}

	public void addApply(Apply apply) {
		session.insert("mapping.JobMapper.addApply", apply);
	}

	/**
	 * 添加大类
	 *
	 * @param classify
	 */
	public void addClassify(Classify classify) {
		session.insert("mapping.JobMapper.addClassify", classify);
	}

	/**
	 * 更新大类
	 *
	 * @param classify
	 */
	public void updateClassify(Classify classify) {
		session.insert("mapping.JobMapper.updateClassify", classify);
	}

	/**
	 * 删除大类
	 *
	 * @param id
	 */
	public void deleteClassify(int id) {
		session.insert("mapping.JobMapper.deleteClassify", id);
	}

	/**
	 * 添加小类
	 *
	 * @param position
	 */
	public void addPosition(Position position) {
		session.insert("mapping.JobMapper.addPosition", position);
	}

	/**
	 * 更新小类
	 *
	 * @param position
	 */
	public void updatePosition(Position position) {
		session.insert("mapping.JobMapper.updatePosition", position);
	}

	/**
	 * 删除小类
	 *
	 * @param id
	 */
	public void deletePosition(int id) {
		session.insert("mapping.JobMapper.deletePosition", id);
	}

	/**
	 * 查询职位
	 *
	 * @return
	 */
	public List<Job> searchJobsByPid(int[] p_ids, int time, int l, int h, int page,String filter,int state) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_ids", p_ids);
		map.put("l", l);
		map.put("h", h);
		map.put("page", page);
		map.put("start", (page - 1) * Common.COUNT);
		map.put("count", Common.COUNT);
		map.put("time", time);
		map.put("filter",filter);
		map.put("state",state);

		List<Job> jobs = session.selectList("mapping.JobMapper.searchJobs", map);
		return jobs;
	}

	/**
	 * 更新职位信息
	 *
	 * @param job
	 */
	public void updateJob(Job job) {
		session.update("mapping.JobMapper.updateJob", job);
	}

	/**
	 * 增加职位
	 *
	 * @param job
	 */
	public void addJob(Job job) {
		session.insert("mapping.JobMapper.addJob", job);
	}

	/**
	 * 删除职位
	 *
	 * @param ids
	 */
	public int deleteJobs(int[] ids) {
		int count = 0;
		count = session.delete("mapping.JobMapper.deleteJobs", ids);
		return count;
	}

	/**
	 * 获取总条数
	 *
	 * @param p_ids
	 * @param l
	 * @param h
	 * @return
	 */
	public int getJobCount(int[] p_ids, int time, int l, int h,String filter,int state) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_ids", p_ids);
		map.put("l", l);
		map.put("h", h);
		map.put("time", time);
		map.put("filter",filter);
		map.put("state",state);

		// int count = session.selectOne("mapping.JobMapper.getJobCount", map);
		return 0;
	}

	/**
	 * 模糊搜索工作
	 *
	 * @param keyword
	 * @param l
	 * @param h       @return
	 */
	public List<Job> searchJobs(String keyword, int page, int l, int h, int time, int c_id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("keyword", keyword);
		map.put("page", page);
		map.put("l", l);
		map.put("h", h);
		map.put("start", (page - 1) * Common.COUNT);
		map.put("count", 10);
		map.put("c_id", c_id);
		map.put("time", time);

		List<Job> jobs = session.selectList("mapping.JobMapper.vagueSearchJobs", map);
		return jobs;
	}

	/**
	 * 查询大类下的所有小类
	 *
	 * @param c_id
	 * @return
	 */
	public List<Position> searchPositions(int c_id) {
		List<Position> positions = null;
		Position position = new Position();
		position.setcId(c_id);
		positions = session.selectList("mapping.JobMapper.searchPositions", position);
		return positions;
	}

	/**
	 * 获取总条数
	 *
	 * @param keyword
	 * @return
	 */
	public int getJobCount(String keyword, int l, int h, int time, int c_id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("keyword", keyword);
		map.put("l", l);
		map.put("h", h);
		map.put("c_id", c_id);
		map.put("time", time);

		// int count = session.selectOne("mapping.JobMapper.getVagueJobCount", map);
		return 0;
	}

	/**
	 * 获取评论条数
	 *
	 * @param id
	 * @return
	 */
	public int getCommentCount(int id) {
		// int count = session.selectOne("mapping.JobMapper.getCommentCount", id);
		return 0;
	}

	/**
	 * 获取指定页的评论
	 *
	 * @param id
	 * @param page
	 * @return
	 */
	public List<Comment> getComments(int id, int page) {
		List<Comment> comments = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("page", page);
		map.put("start", (page - 1) * Common.SMALL_COUNT);
		map.put("count", Common.SMALL_COUNT);

		comments = session.selectList("mapping.JobMapper.getCommentsPage", map);
		return comments;
	}

	/**
	 * 返回一个公司的所有职位
	 *
	 * @param company_id
	 * @return
	 */
	public List<Job> getJobsByCompany(int company_id, int page) {
		List<Job> jobs = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", company_id);
		map.put("page", page);
		map.put("start", (page - 1) * Common.SMALL_COUNT);
		map.put("count", Common.SMALL_COUNT);
		jobs = session.selectList("mapping.JobMapper.getJobsByCompany", map);
		return jobs;
	}

	/**
	 * 根据id返回小类
	 *
	 * @param id
	 * @return
	 */
	public Position getPositionById(int id) {
		Position position = session.selectOne("mapping.JobMapper.getPositionById", id);
		return position;
	}

	/**
	 * 获取所有的院系信息
	 *
	 * @return
	 */
	public List<Major> getSchoolsAndMajors() {
		List<Major> majors = null;
		majors = session.selectList("mapping.JobMapper.getSchoolsAndMajors");
		return majors;
	}

	/**
	 * 获取一个公司的职位数量
	 *
	 * @param id
	 * @return
	 */
	public int getCompanyJobCount(int id) {
		// int count = session.selectOne("mapping.JobMapper.getCompanyJobCount", id);
		return 0;
	}

	/**
	 * 改变职位的运行状态
	 *
	 * @param j_id
	 * @param status
	 */
	public void changeJobStatus(int j_id, int status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("j_id", j_id);
		map.put("status", status);

		session.update("mapping.JobMapper.changeJobStatus", map);
	}

	/**
	 * 获取去投递该职位的简历
	 *
	 * @param id
	 * @param page
	 * @return
	 */
	public List<Apply> getAppliesByCompany(int id, int page) {
		List<Apply> applies = null;

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("page", page);
		map.put("start", (page - 1) * Common.COUNT);
		map.put("count", Common.COUNT);

		applies = session.selectList("mapping.JobMapper.getAppliesByCompany", map);

		return applies;
	}

	/**
	 * 获取投递该公司简历的个数
	 *
	 * @param id
	 * @return
	 */
	public int getCompanyApplyCount(int id) {
		int count = 0;
		// count = session.selectOne("mapping.JobMapper.getCompanyApplyCount", id);

		return count;
	}

	/**
	 * 更新简历的投递状态
	 *
	 * @param j_id
	 * @param r_id
	 * @param state
	 */
	public void updateApply(int j_id, int r_id, int state) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("j_id", j_id);
		map.put("r_id", r_id);
		map.put("state", state);

		session.update("mapping.JobMapper.updateApply", map);
	}

	/**
	 * 获取搜索到的简历数量
	 * @param grade
	 * @param time
	 * @param salary
	 * @param school
	 * @return
	 */
	public int getResumeCount(int grade, int time, String salary, int school,String filter,boolean push) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("grade", grade);
		map.put("time", time);
		map.put("salary", salary);
		map.put("grade", grade);
		map.put("school", school);
		map.put("filter",filter);
		if(push) {
			map.put("push",1);
		} else {
			map.put("push",0);
		}

		int count = 0;
		// count = session.selectOne("mapping.ResumeMapper.getResumeCount",map);

		return count;
	}

	/**
	 * 推荐职位
	 * @param time
	 * @param p_id
	 * @return
	 */
	public List<Job> getRecommendJobs(int time, int p_id) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("time",time);
		map.put("p_id",p_id);
		map.put("count",Common.SMALL_COUNT);

		List<Job> jobs = null;
		jobs = session.selectList("mapping.JobMapper.getRecommendJobs",map);
		return jobs;
	}
}