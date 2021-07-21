package cn.lwjppz.mindstorm.education.controller;


import cn.lwjppz.mindstorm.common.core.support.CommonResult;
import cn.lwjppz.mindstorm.education.model.vo.topic.TopicVO;
import cn.lwjppz.mindstorm.education.service.TopicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 知识点表 前端控制器
 * </p>
 *
 * @author lwj
 * @since 2021-07-20
 */
@RestController
@RequestMapping("/education/topic")
@Api(tags = "知识点Api接口")
public class TopicController {

    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping("/list")
    @ApiOperation("获取所有知识点")
    public CommonResult listTopic() {
        var topics = topicService.convertTopicDTO(topicService.listTopics());
        return CommonResult.ok().data("topics", topics);
    }

    @GetMapping("/list/select")
    @ApiOperation("获取所有知识点（Select选择器组件）")
    public CommonResult listSelectTopic() {
        var topicSelects = topicService.convertToTopicSelectDTO(topicService.listTopics());
        return CommonResult.ok().data("topicSelects", topicSelects);
    }

    @PostMapping("/create")
    @ApiOperation("新增知识点")
    public CommonResult createTopic(@ApiParam("知识点信息") @RequestBody TopicVO topicVO) {
        var topic = topicService.createTopic(topicVO);
        return CommonResult.ok().data("topic", topic);
    }

    @PostMapping("/update")
    @ApiOperation("更新知识点")
    public CommonResult updateTopic(@ApiParam("知识点信息") @RequestBody TopicVO topicVO) {
        var success = topicService.updateTopic(topicVO);
        return CommonResult.ok().data("success", success);
    }

    @DeleteMapping("/delete/{topicId}")
    @ApiOperation("删除知识点")
    public CommonResult deleteTopic(@ApiParam("知识点Id") @PathVariable("topicId") String topicId) {
        var success = topicService.deleteTopic(topicId);
        return CommonResult.ok().data("success", success);
    }

}

