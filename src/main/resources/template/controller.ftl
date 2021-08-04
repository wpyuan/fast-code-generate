package ${controllerPackage};

import ${entityPackage}.${entityClassName};
import ${servicePackage}.${entityClassName}Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.github.dc.common.base.controller.DefaultController;

import java.util.List;

/**
 *  ${tableDesc!} 控制器
 *
 * @author ${author}
 * @date ${date}
 */
@RestController
@RequestMapping("/${requestMappingUrl}")
@Slf4j
public class ${entityClassName}Controller extends DefaultController<${entityClassName}>{

    @Autowired
    private ${entityClassName}Service service;

}