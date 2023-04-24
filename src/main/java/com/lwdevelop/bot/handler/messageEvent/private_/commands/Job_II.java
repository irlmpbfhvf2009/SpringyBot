package com.lwdevelop.bot.handler.messageEvent.private_.commands;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import com.lwdevelop.bot.utils.Common;
import com.lwdevelop.bot.utils.KeyboardButton;
import com.lwdevelop.dto.JobPostingDTO;
import com.lwdevelop.dto.JobSeekerDTO;
import com.lwdevelop.entity.ChannelMessageIdPostCounts;
import com.lwdevelop.entity.JobPosting;
import com.lwdevelop.entity.JobSeeker;
import com.lwdevelop.entity.JobUser;
import com.lwdevelop.entity.RobotChannelManagement;
import com.lwdevelop.entity.SpringyBot;
import com.lwdevelop.service.impl.JobManagementServiceImpl;
import com.lwdevelop.service.impl.SpringyBotServiceImpl;
import com.lwdevelop.utils.SpringUtils;
import org.telegram.telegrambots.meta.api.objects.Message;

public class Job_II {

    @Autowired
    private JobManagementServiceImpl jobManagementServiceImpl = SpringUtils.getApplicationContext()
            .getBean(JobManagementServiceImpl.class);

    @Autowired
    private SpringyBotServiceImpl springyBotServiceImpl = SpringUtils.getApplicationContext()
            .getBean(SpringyBotServiceImpl.class);

    public void setResponse_jobPosting_management(Common common) {

        Long id = common.getSpringyBotId();
        String userId = String.valueOf(common.getUpdate().getMessage().getChatId());

        SendMessage response = new SendMessage();
        response.setChatId(userId);

        JobPosting jobPosting = jobManagementServiceImpl.findByUserIdAndBotIdWithJobPosting(userId,
                String.valueOf(id));

        SpringyBot springyBot = springyBotServiceImpl.findById(id).orElseThrow();
        JobUser jobUser = springyBot.getJobUser().stream().filter(j -> j.getUserId().equals(userId)).findFirst()
                .orElseGet(() -> {
                    JobUser ju = new JobUser();
                    ju.setUserId(userId);
                    springyBot.getJobUser().add(ju);
                    return ju;
                });
        String company = "", position = "", baseSalary = "", commission = "", workTime = "", requirements = "",
                location = "", flightNumber = "";
        if (jobPosting == null){
            response.setText("招聘人才\n\n" + "公司：\n" + "职位：\n" + "底薪：\n" + "提成：\n" + "上班时间：\n" + "要求内容：\n"
                    + "🐌地址：\n" + "✈️咨询飞机号：");
        }else {
            company = Optional.ofNullable(jobPosting.getCompany()).orElse("");
            position = Optional.ofNullable(jobPosting.getPosition()).orElse("");
            baseSalary = Optional.ofNullable(jobPosting.getBaseSalary()).orElse("");
            commission = Optional.ofNullable(jobPosting.getCommission()).orElse("");
            workTime = Optional.ofNullable(jobPosting.getWorkTime()).orElse("");
            requirements = Optional.ofNullable(jobPosting.getRequirements()).orElse("");
            location = Optional.ofNullable(jobPosting.getLocation()).orElse("");
            flightNumber = Optional.ofNullable(jobPosting.getFlightNumber()).orElse("");
            response.setText(
                    "招聘人才\n\n" + "公司：" + company + "\n" + "职位：" + position + "\n" + "底薪："
                            + baseSalary + "\n" + "提成：" + commission + "\n" + "上班时间："
                            + workTime + "\n" + "要求内容：" + requirements + "\n"
                            + "🐌地址：" + location + "\n" + "✈️咨询飞机号：" + flightNumber);
        }

        common.sendResponseAsync(response);

        response.setText("提醒：\n"+"請複製上列信息到輸入框並進行編輯，編輯完畢請按發送");
        common.sendResponseAsync(response);

    }

    public void setResponse_jobSeeker_management(Common common) {

        Long id = common.getSpringyBotId();
        String userId = String.valueOf(common.getUpdate().getMessage().getChatId());

        SendMessage response = new SendMessage();
        response.setChatId(userId);


        JobPosting jobPosting = jobManagementServiceImpl.findByUserIdAndBotIdWithJobPosting(userId,
                String.valueOf(id));

        SpringyBot springyBot = springyBotServiceImpl.findById(id).orElseThrow();
        JobUser jobUser = springyBot.getJobUser().stream().filter(j -> j.getUserId().equals(userId)).findFirst()
                .orElseGet(() -> {
                    JobUser ju = new JobUser();
                    ju.setUserId(userId);
                    springyBot.getJobUser().add(ju);
                    return ju;
                });

        response.setText(
                "求职人员\n\n姓名：\n男女：\n出生_年_月_日：\n年龄：\n国籍：\n学历：\n技能：\n目标职位：\n手上有什么资源：\n期望薪资：\n工作经历：\n自我介绍：\n✈️咨询飞机号：");
        common.sendResponseAsync(response);

        response.setText("提醒：\n"+"請複製上列信息到輸入框並進行編輯，編輯完畢請按發送");
        common.sendResponseAsync(response);

    }

    public void generateTextJobPosting(Common common){
        Message message = common.getUpdate().getMessage();
        String text = message.getText();
        // 将文本内容按行分割成字符串数组
        String[] lines = text.split("\\r?\\n");

        JobPosting jobPosting = jobManagementServiceImpl.findByUserIdAndBotIdWithJobPosting(
                String.valueOf(message.getChatId()), String.valueOf(common.getSpringyBotId()));
        // 创建一个新的 JobPosting 实例
        if (jobPosting != null) {
            //  清除舊資料
            jobPosting = this.initJobPosting(jobPosting);

            for (String line : lines) {
                String[] parts = line.split("：");
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();

                    switch (key) {
                        case "公司":
                            jobPosting.setCompany(value);
                            break;
                        case "职位":
                            jobPosting.setPosition(value);
                            break;
                        case "底薪":
                            jobPosting.setBaseSalary(value);
                            break;
                        case "提成":
                            jobPosting.setCommission(value);
                            break;
                        case "上班时间":
                            jobPosting.setWorkTime(value);
                            break;
                        case "要求内容":
                            jobPosting.setRequirements(value);
                            break;
                        case "🐌地址":
                            jobPosting.setLocation(value);
                            break;
                        case "✈️咨询飞机号":
                            jobPosting.setFlightNumber(value);
                            break;
                        default:
                            // 未知键值对，可以忽略或抛出异常
                            break;
                    }
                }
            }
        } else {
            jobPosting = new JobPosting();
            // 遍历字符串数组，将冒号后面的值设置到实体对应的字段中
            for (String line : lines) {
                String[] parts = line.split("：");
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();

                    switch (key) {
                        case "公司":
                            jobPosting.setCompany(value);
                            break;
                        case "职位":
                            jobPosting.setPosition(value);
                            break;
                        case "底薪":
                            jobPosting.setBaseSalary(value);
                            break;
                        case "提成":
                            jobPosting.setCommission(value);
                            break;
                        case "上班时间":
                            jobPosting.setWorkTime(value);
                            break;
                        case "要求内容":
                            jobPosting.setRequirements(value);
                            break;
                        case "🐌地址":
                            jobPosting.setLocation(value);
                            break;
                        case "✈️咨询飞机号":
                            jobPosting.setFlightNumber(value);
                            break;
                        default:
                            // 未知键值对，可以忽略或抛出异常
                            break;
                    }
                }
            }
        }
        jobPosting.setBotId(String.valueOf(common.getSpringyBotId()));
        jobPosting.setUserId(String.valueOf(message.getChatId()));
        jobPosting.setLastMessageId(message.getMessageId());
        //  處理資料表
        Long id = common.getSpringyBotId();
        String userId = String.valueOf(common.getUpdate().getMessage().getChatId());
        SpringyBot springyBot = springyBotServiceImpl.findById(id).orElseThrow();
        JobUser jobUser = springyBot.getJobUser().stream().filter(j -> j.getUserId().equals(userId)).findFirst()
                .orElseGet(() -> {
                    JobUser ju = new JobUser();
                    ju.setUserId(userId);
                    springyBot.getJobUser().add(ju);
                    // 存到job_user_job_posting表
                    springyBotServiceImpl.save(springyBot);
                    return ju;
                });
        jobUser.getJobPosting().add(jobPosting);
        jobManagementServiceImpl.saveJobPosting(jobPosting);

        Iterator<RobotChannelManagement> iterator = springyBot.getRobotChannelManagement().iterator();
        while (iterator.hasNext()) {
            SendMessage response = new SendMessage();
            String chatId = String.valueOf(iterator.next().getChannelId());
            response.setChatId(chatId);
            StringBuilder sb = new StringBuilder();

            appendIfNotEmpty(sb, "公司：", jobPosting.getCompany());
            appendIfNotEmpty(sb, "职位：", jobPosting.getPosition());
            appendIfNotEmpty(sb, "底薪：", jobPosting.getBaseSalary());
            appendIfNotEmpty(sb, "提成：", jobPosting.getCommission());
            appendIfNotEmpty(sb, "上班时间：", jobPosting.getWorkTime());
            appendIfNotEmpty(sb, "要求内容：", jobPosting.getRequirements());
            appendIfNotEmpty(sb, "🐌地址：", jobPosting.getLocation());
            appendIfNotEmpty(sb, "✈️咨询飞机号：", jobPosting.getFlightNumber());
            String result = sb.toString().trim();

            response.setText(result);
            common.sendResponseAsync(response);
        }

    }


    public void generateTextJobSeeker(Common common) {
        Message message = common.getUpdate().getMessage();
        String text = message.getText();

        String[] lines = text.split("\\r?\\n");

        JobSeeker jobSeeker = jobManagementServiceImpl.findByUserIdAndBotIdWithJobSeeker(
                String.valueOf(message.getChatId()), String.valueOf(common.getSpringyBotId()));

        if (jobSeeker != null) {
            //  清除舊資料
            jobSeeker = this.initJobSeeker(jobSeeker);

            for (String line : lines) {
                String[] parts = line.split("：");
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();

                    switch (key) {
                        case "姓名":
                            jobSeeker.setName(value);
                            break;
                        case "男女":
                            jobSeeker.setGender(value);
                            break;
                        case "出生_年_月_日":
                            jobSeeker.setDateOfBirth(value);
                            break;
                        case "年龄":
                            jobSeeker.setAge(value);
                            break;
                        case "国籍":
                            jobSeeker.setNationality(value);
                            break;
                        case "学历":
                            jobSeeker.setEducation(value);
                            break;
                        case "技能":
                            jobSeeker.setSkills(value);
                            break;
                        case "目标职位":
                            jobSeeker.setTargetPosition(value);
                            break;
                        case "手上有什么资源":
                            jobSeeker.setResources(value);
                            break;
                        case "期望薪资":
                            jobSeeker.setExpectedSalary(value);
                            break;
                        case "工作经历":
                            jobSeeker.setWorkExperience(value);
                            break;
                        case "自我介绍":
                            jobSeeker.setSelfIntroduction(value);
                            break;
                        case "✈️咨询飞机号":
                            jobSeeker.setFlightNumber(value);
                            break;
                        default:
                            // 未知键值对，可以忽略或抛出异常
                            break;
                    }
                }
            }
        } else {
            jobSeeker = new JobSeeker();

            // 遍历字符串数组，将冒号后面的值设置到实体对应的字段中
            for (String line : lines) {
                String[] parts = line.split("：");
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();

                    switch (key) {
                        case "姓名":
                            jobSeeker.setName(value);
                            break;
                        case "男女":
                            jobSeeker.setGender(value);
                            break;
                        case "出生_年_月_日":
                            jobSeeker.setDateOfBirth(value);
                            break;
                        case "年龄":
                            jobSeeker.setAge(value);
                            break;
                        case "国籍":
                            jobSeeker.setNationality(value);
                            break;
                        case "学历":
                            jobSeeker.setEducation(value);
                            break;
                        case "技能":
                            jobSeeker.setSkills(value);
                            break;
                        case "目标职位":
                            jobSeeker.setTargetPosition(value);
                            break;
                        case "手上有什么资源":
                            jobSeeker.setResources(value);
                            break;
                        case "期望薪资":
                            jobSeeker.setExpectedSalary(value);
                            break;
                        case "工作经历":
                            jobSeeker.setWorkExperience(value);
                            break;
                        case "自我介绍":
                            jobSeeker.setSelfIntroduction(value);
                            break;
                        case "✈️咨询飞机号":
                            jobSeeker.setFlightNumber(value);
                            break;
                        default:
                            // 未知键值对，可以忽略或抛出异常
                            break;
                    }
                }
            }
        }

        jobSeeker.setBotId(String.valueOf(common.getSpringyBotId()));
        jobSeeker.setUserId(String.valueOf(message.getChatId()));
        jobSeeker.setLastMessageId(message.getMessageId());

        //  處理資料表
        Long id = common.getSpringyBotId();
        String userId = String.valueOf(common.getUpdate().getMessage().getChatId());
        SpringyBot springyBot = springyBotServiceImpl.findById(id).orElseThrow();
        JobUser jobUser = springyBot.getJobUser().stream().filter(j -> j.getUserId().equals(userId)).findFirst()
                .orElseGet(() -> {
                    JobUser ju = new JobUser();
                    ju.setUserId(userId);
                    springyBot.getJobUser().add(ju);
                    return ju;
                });
        jobUser.getJobSeeker().add(jobSeeker);
        jobManagementServiceImpl.saveJobSeeker(jobSeeker);

        Iterator<RobotChannelManagement> iterator = springyBot.getRobotChannelManagement().iterator();
        while (iterator.hasNext()) {
            SendMessage response = new SendMessage();
            String chatId = String.valueOf(iterator.next().getChannelId());
            response.setChatId(chatId);
            StringBuilder sb = new StringBuilder();
            appendIfNotEmpty(sb, "姓名：", jobSeeker.getName());
            appendIfNotEmpty(sb, "男女：", jobSeeker.getGender());
            appendIfNotEmpty(sb, "出生_年_月_日：", jobSeeker.getDateOfBirth());
            appendIfNotEmpty(sb, "年龄：", jobSeeker.getAge());
            appendIfNotEmpty(sb, "国籍：", jobSeeker.getNationality());
            appendIfNotEmpty(sb, "学历：", jobSeeker.getEducation());
            appendIfNotEmpty(sb, "技能：", jobSeeker.getSkills());
            appendIfNotEmpty(sb, "目标职位：", jobSeeker.getTargetPosition());
            appendIfNotEmpty(sb, "手上有什么资源：", jobSeeker.getResources());
            appendIfNotEmpty(sb, "期望薪资：", jobSeeker.getExpectedSalary());
            appendIfNotEmpty(sb, "工作经历：", jobSeeker.getWorkExperience());
            appendIfNotEmpty(sb, "自我介绍：", jobSeeker.getSelfIntroduction());
            appendIfNotEmpty(sb, "✈️咨询飞机号：", jobSeeker.getFlightNumber());
            String result = sb.toString().trim();

            response.setText(result);

            Integer messageId = common.sendResponseAsync(response);

            ChannelMessageIdPostCounts channelMessageIdPostCounts = jobManagementServiceImpl
                .findByBotIdAndUserIdAndTypeWithChannelMessageIdPostCounts(String.valueOf(id),
                        userId, "jobSeeker");

            if (channelMessageIdPostCounts == null){
                channelMessageIdPostCounts = new ChannelMessageIdPostCounts();
                channelMessageIdPostCounts.setChannelId(iterator.next().getChannelId());
                channelMessageIdPostCounts.setChannelTitle(iterator.next().getChannelTitle());
                channelMessageIdPostCounts.setPostCount(1);
                channelMessageIdPostCounts.setMessageId(messageId);
                channelMessageIdPostCounts.setBotId(String.valueOf(id));
                channelMessageIdPostCounts.setType("jobSeeker");
                channelMessageIdPostCounts.setUserId(userId);

            }else{
                channelMessageIdPostCounts.setChannelId(iterator.next().getChannelId());
                channelMessageIdPostCounts.setChannelTitle(iterator.next().getChannelTitle());
                channelMessageIdPostCounts.setPostCount(1);
                channelMessageIdPostCounts.setMessageId(messageId);
                channelMessageIdPostCounts.setBotId(String.valueOf(id));
                channelMessageIdPostCounts.setType("jobSeeker");
                channelMessageIdPostCounts.setUserId(userId);

            }

            jobManagementServiceImpl.saveChannelMessageIdPostCounts(channelMessageIdPostCounts);
            jobSeeker.getChannelMessageIdPostCounts().add(channelMessageIdPostCounts);
            jobManagementServiceImpl.saveJobSeeker(jobSeeker);
        }

    }
    private void appendIfNotEmpty(StringBuilder sb, String label, String value) {
        if (value != null && !value.isEmpty()) {
            sb.append(label).append(value).append("\n");
        }
    }

    private JobPosting initJobPosting(JobPosting jobPosting){
        jobPosting.setCompany("");
        jobPosting.setPosition("");
        jobPosting.setBaseSalary("");
        jobPosting.setCommission("");
        jobPosting.setWorkTime("");
        jobPosting.setRequirements("");
        jobPosting.setLocation("");
        jobPosting.setFlightNumber("");
        return jobPosting;
    }

    private JobSeeker initJobSeeker(JobSeeker jobSeeker){
        jobSeeker.setName("");
        jobSeeker.setGender("");
        jobSeeker.setDateOfBirth("");
        jobSeeker.setAge("");
        jobSeeker.setNationality("");
        jobSeeker.setEducation("");
        jobSeeker.setSkills("");
        jobSeeker.setTargetPosition("");
        jobSeeker.setResources("");
        jobSeeker.setExpectedSalary("");
        jobSeeker.setWorkExperience("");
        jobSeeker.setSelfIntroduction("");
        jobSeeker.setFlightNumber("");
        return jobSeeker;
    }

    public void setResponse_edit_jobPosting_management(Common common) {

        Long id = common.getSpringyBotId();
        String userId = String.valueOf(common.getUpdate().getMessage().getChatId());

        SendMessage response = new SendMessage();
        response.setChatId(userId);

        List<ChannelMessageIdPostCounts> channelMessageIdPostCounts = jobManagementServiceImpl
                .findAllByBotIdAndUserIdAndTypeWithChannelMessageIdPostCounts(String.valueOf(id),
                        userId, "jobPosting");
        Map<String, Integer> channelInfo = channelMessageIdPostCounts.stream()
                .collect(Collectors.toMap(
                        ChannelMessageIdPostCounts::getChannelTitle,
                        ChannelMessageIdPostCounts::getPostCount));

        String alert = channelInfo.entrySet().stream()
                .map(entry -> entry.getValue() != 0
                        ? entry.getKey() + " 发布了" + entry.getValue() + "则 [招聘人才] 信息\n"
                        : "")
                .collect(Collectors.joining());
        if (!alert.isEmpty()) {
            response.setText("提醒：您已经在:\n" + alert);
            response.setDisableNotification(true);
            response.setDisableWebPagePreview(false);
            common.sendResponseAsync(response);
        }
        JobPosting jobPosting = jobManagementServiceImpl.findByUserIdAndBotIdWithJobPosting(userId,
                String.valueOf(id));

        SpringyBot springyBot = springyBotServiceImpl.findById(id).orElseThrow();
        JobUser jobUser = springyBot.getJobUser().stream().filter(j -> j.getUserId().equals(userId)).findFirst()
                .orElseGet(() -> {
                    JobUser ju = new JobUser();
                    ju.setUserId(userId);
                    springyBot.getJobUser().add(ju);
                    return ju;
                });

        JobPostingDTO jobPostingDTO = new JobPostingDTO(userId, String.valueOf(id));
        String company = "", position = "", baseSalary = "", commission = "", workTime = "", requirements = "",
                location = "", flightNumber = "";

        if (jobPosting != null) {
            company = Optional.ofNullable(jobPosting.getCompany()).orElse("");
            position = Optional.ofNullable(jobPosting.getPosition()).orElse("");
            baseSalary = Optional.ofNullable(jobPosting.getBaseSalary()).orElse("");
            commission = Optional.ofNullable(jobPosting.getCommission()).orElse("");
            workTime = Optional.ofNullable(jobPosting.getWorkTime()).orElse("");
            requirements = Optional.ofNullable(jobPosting.getRequirements()).orElse("");
            location = Optional.ofNullable(jobPosting.getLocation()).orElse("");
            flightNumber = Optional.ofNullable(jobPosting.getFlightNumber()).orElse("");
            response.setText(
                    "招聘人才\n\n" + "公司：" + company + "\n" + "职位：" + position + "\n" + "底薪："
                            + baseSalary + "\n" + "提成：" + commission + "\n" + "上班时间："
                            + workTime + "\n" + "要求内容：" + requirements + "\n"
                            + "🐌地址：" + location + "\n" + "✈️咨询飞机号：" + flightNumber);
            jobPostingDTO = new JobPostingDTO(userId, String.valueOf(id), company, position, baseSalary,
                    commission, workTime, requirements, location, flightNumber);
            response.setReplyMarkup(new KeyboardButton().keyboard_jobPosting(jobPostingDTO,true));
            // response.setReplyMarkup(new KeyboardButton().keyboard_editJobPosting(jobPostingDTO));
            Integer messageId = common.sendResponseAsync(response);
            jobPosting.setLastMessageId(messageId);
            jobUser.getJobPosting().add(jobPosting);
            jobManagementServiceImpl.saveJobPosting(jobPosting);
        } else {
            response.setText("招聘人才\n\n" + "公司：\n" + "职位：\n" + "底薪：\n" + "提成：\n" + "上班时间：\n" + "要求内容：\n"
                    + "🐌地址：\n" + "✈️咨询飞机号：");
            response.setReplyMarkup(new KeyboardButton().keyboard_jobPosting(jobPostingDTO,false));
            // response.setReplyMarkup(new KeyboardButton().keyboard_jobPosting(jobPostingDTO));

            JobPosting jp = new JobPosting(userId, String.valueOf(id),
                    common.sendResponseAsync(response));
            jobUser.getJobPosting().add(jp);
            jobManagementServiceImpl.saveJobPosting(jp);
            springyBotServiceImpl.save(springyBot);
        }
    }

    public void setResponse_edit_jobSeeker_management(Common common) {

        Long id = common.getSpringyBotId();
        String userId = String.valueOf(common.getUpdate().getMessage().getChatId());

        SendMessage response = new SendMessage();
        response.setChatId(String.valueOf(common.getUpdate().getMessage().getChatId()));

        List<ChannelMessageIdPostCounts> channelMessageIdPostCounts = jobManagementServiceImpl
                .findAllByBotIdAndUserIdAndTypeWithChannelMessageIdPostCounts(String.valueOf(id),
                        userId, "jobSeeker");
        Map<String, Integer> channelInfo = channelMessageIdPostCounts.stream()
                .collect(Collectors.toMap(
                        ChannelMessageIdPostCounts::getChannelTitle,
                        ChannelMessageIdPostCounts::getPostCount));

        // String alert = channelInfo.entrySet().stream()
        // .map(entry -> entry.getKey() + " 发布了" + entry.getValue() + "则 [求职人员] 信息\n")
        // .collect(Collectors.joining());

        String alert = channelInfo.entrySet().stream()
                .map(entry -> entry.getValue() != 0
                        ? entry.getKey() + " 发布了" + entry.getValue() + "则 [求职人员] 信息\n"
                        : "")
                .collect(Collectors.joining());
        if (!alert.isEmpty()) {
            response.setText("提醒：您已经在:\n" + alert);
            response.setDisableNotification(false);
            response.setDisableWebPagePreview(false);
            common.sendResponseAsync(response);
        }
        JobSeeker jobSeeker = jobManagementServiceImpl.findByUserIdAndBotIdWithJobSeeker(userId,
                String.valueOf(id));

        SpringyBot springyBot = springyBotServiceImpl.findById(id).orElseThrow();

        JobUser jobUser = springyBot.getJobUser().stream().filter(j -> j.getUserId().equals(userId)).findFirst()
                .orElseGet(() -> {
                    JobUser ju = new JobUser();
                    ju.setUserId(userId);
                    springyBot.getJobUser().add(ju);
                    return ju;
                });

        JobSeekerDTO jobSeekerDTO = new JobSeekerDTO(userId, String.valueOf(id));
        String name = "", gender = "", dateOfBirth = "", age = "", nationality = "", education = "",
                skills = "", targetPosition = "", resources = "", expectedSalary = "",
                workExperience = "", selfIntroduction = "", flightNumber = "";

        if (jobSeeker != null) {
            name = Optional.ofNullable(jobSeeker.getName()).orElse("");
            gender = Optional.ofNullable(jobSeeker.getGender()).orElse("");
            dateOfBirth = Optional.ofNullable(jobSeeker.getDateOfBirth()).orElse("");
            age = Optional.ofNullable(jobSeeker.getAge()).orElse("");
            nationality = Optional.ofNullable(jobSeeker.getNationality()).orElse("");
            education = Optional.ofNullable(jobSeeker.getEducation()).orElse("");
            skills = Optional.ofNullable(jobSeeker.getSkills()).orElse("");
            targetPosition = Optional.ofNullable(jobSeeker.getTargetPosition()).orElse("");
            resources = Optional.ofNullable(jobSeeker.getResources()).orElse("");
            expectedSalary = Optional.ofNullable(jobSeeker.getExpectedSalary()).orElse("");
            workExperience = Optional.ofNullable(jobSeeker.getWorkExperience()).orElse("");
            selfIntroduction = Optional.ofNullable(jobSeeker.getSelfIntroduction()).orElse("");
            flightNumber = Optional.ofNullable(jobSeeker.getFlightNumber()).orElse("");

            jobSeekerDTO = new JobSeekerDTO(userId, String.valueOf(id), name, gender, dateOfBirth, age,
                    nationality, education, skills, targetPosition, resources, expectedSalary,
                    workExperience, selfIntroduction, flightNumber);

            response.setText("求职人员\n\n姓名：" + name + "\n男女：" + gender + "\n出生_年_月_日："
                    + dateOfBirth
                    + "\n年龄：" + age + "\n国籍：" + nationality + "\n学历：" + education
                    + "\n技能：" + skills + "\n目标职位：" + targetPosition + "\n手上有什么资源："
                    + resources + "\n期望薪资：" + expectedSalary + "\n工作经历："
                    + workExperience + "\n自我介绍：" + selfIntroduction + "\n✈️咨询飞机号：" + flightNumber);
            response.setReplyMarkup(new KeyboardButton().keyboard_jobSeeker(jobSeekerDTO,true));
            // response.setReplyMarkup(new KeyboardButton().keyboard_editJobSeeker(jobSeekerDTO));
            Integer messageId = common.sendResponseAsync(response);
            jobSeeker.setLastMessageId(messageId);
            jobUser.getJobSeeker().add(jobSeeker);
            jobManagementServiceImpl.saveJobSeeker(jobSeeker);
        } else {
            response.setText(
                    "求职人员\n\n姓名：\n男女：\n出生_年_月_日：\n年龄：\n国籍：\n学历：\n技能：\n目标职位：\n手上有什么资源：\n期望薪资：\n工作经历：\n自我介绍：\n✈️咨询飞机号：");
            response.setReplyMarkup(new KeyboardButton().keyboard_jobSeeker(jobSeekerDTO,true));
            // response.setReplyMarkup(new KeyboardButton().keyboard_jobSeeker(jobSeekerDTO));
            JobSeeker js = new JobSeeker(userId, String.valueOf(id),
                    common.sendResponseAsync(response));
            jobUser.getJobSeeker().add(js);
            jobManagementServiceImpl.saveJobSeeker(js);
            springyBotServiceImpl.save(springyBot);
        }
    }
}
