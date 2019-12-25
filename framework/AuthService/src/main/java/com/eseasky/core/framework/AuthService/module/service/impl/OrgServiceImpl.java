package com.eseasky.core.framework.AuthService.module.service.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jodd.cache.FIFOCache;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.eseasky.core.framework.AuthService.exception.BusiException.BusiEnum;
import com.eseasky.core.framework.AuthService.exception.BusiException.BusiException;
import com.eseasky.core.framework.AuthService.module.service.OrgService;
import com.eseasky.core.framework.AuthService.protocol.dto.OrgSaveMoreDTO;
import com.eseasky.core.framework.AuthService.protocol.dto.OrgUpdateDTO;
import com.eseasky.core.framework.AuthService.protocol.vo.OrgRowSaveVO;
import com.eseasky.core.framework.AuthService.protocol.vo.OrgSaveByExcelVO;
import com.eseasky.core.starters.organization.persistence.IOrganizeService;
import com.eseasky.core.starters.organization.persistence.entity.OrgInsertInfo;
import com.eseasky.core.starters.organization.persistence.entity.OrganizeQuery;
import com.eseasky.core.starters.organization.persistence.entity.OrganizeUpdateInfo;
import com.eseasky.core.starters.organization.persistence.model.OrganizeDefined;
import com.eseasky.global.utils.SequeceHelper;
import com.eseasky.protocol.auth.entity.DTO.OrgQueryDTO;
import com.eseasky.protocol.auth.entity.DTO.OrgSaveDTO;
import com.eseasky.protocol.auth.entity.DTO.OrgUpByCodeDTO;
import com.eseasky.protocol.auth.entity.VO.MulOrgsVO;
import com.eseasky.protocol.auth.entity.VO.OrgQueryVO;
import com.eseasky.protocol.auth.entity.VO.OrgSaveVO;
import com.google.common.base.Strings;

@Service
public class OrgServiceImpl implements OrgService {
    @Autowired
    private IOrganizeService iOrganizeService;

    @Override
    @Cacheable(value = {"org_code_defined"}, key = "'queryOrg'+#orgQueryDTO", unless = "#result == null")
    public Page<OrgQueryVO> queryOrg(OrgQueryDTO orgQueryDTO) {
        // TODO Auto-generated method stub
        Page<OrgQueryVO> orgQueryVOs = null;
        if (orgQueryDTO != null) {
            OrganizeQuery organizeQuery = new OrganizeQuery();
            BeanUtils.copyProperties(orgQueryDTO, organizeQuery);
            if (orgQueryDTO.getSize() != 0)
                organizeQuery.setPageSize(orgQueryDTO.getSize());
            Page<OrganizeDefined> organizeDefineds = iOrganizeService.queryOrganize(organizeQuery);
            if (organizeDefineds != null) {
                List<OrgQueryVO> orgQueryVOls = organizeDefineds.stream().map(item -> {
                    OrgQueryVO orgQueryVO = new OrgQueryVO();
                    BeanUtils.copyProperties(item, orgQueryVO);
                    return orgQueryVO;
                }).collect(Collectors.toList());
                orgQueryVOs = new PageImpl<OrgQueryVO>(orgQueryVOls, organizeDefineds.getPageable(),
                        organizeDefineds.getTotalElements());
            }
        }
        return orgQueryVOs;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = {"org_code_defined"}, allEntries = true)
    public OrgSaveVO saveOrg(OrgSaveDTO orgSaveDTO) {
        OrgSaveVO orgSaveVO = null;
        if (orgSaveDTO != null) {
//			if (orgSaveDTO.getLevel() != 3) {
            orgSaveVO = checkOrgName(orgSaveDTO);
            if (orgSaveVO != null && orgSaveVO.getId() != null)
                throw new BusiException(BusiEnum.ORGNAME_REPEATABLE);
//			}
            OrgInsertInfo orgInsertInfo = new OrgInsertInfo();
            BeanUtils.copyProperties(orgSaveDTO, orgInsertInfo);
            OrganizeDefined organizeDefined = iOrganizeService.addOrganize(orgInsertInfo);
            if (organizeDefined != null) {
                orgSaveVO = new OrgSaveVO();
                BeanUtils.copyProperties(organizeDefined, orgSaveVO);
            }
        }
        return orgSaveVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = {"org_code_defined"}, allEntries = true)
    public OrgSaveVO updateOrg(OrgUpdateDTO orgUpdateDTO) {
        // TODO Auto-generated method stub
        OrgSaveVO orgSaveVO = null;
        if (orgUpdateDTO != null) {
            if (orgUpdateDTO.getName() != null && orgUpdateDTO.getLevel() != null && orgUpdateDTO.getLevel() != 3) {
                OrgSaveDTO orgSaveDTO = new OrgSaveDTO();
                BeanUtils.copyProperties(orgUpdateDTO, orgSaveDTO);
                orgSaveVO = checkOrgName(orgSaveDTO);
                if (orgSaveVO != null && orgSaveVO.getId() != null && orgUpdateDTO.getId().longValue() != orgSaveVO.getId().longValue())
                    throw new BusiException(BusiEnum.ORGNAME_REPEATABLE);
            }
            OrganizeUpdateInfo orgInsertInfo = new OrganizeUpdateInfo();
            BeanUtils.copyProperties(orgUpdateDTO, orgInsertInfo);
            OrganizeDefined organizeDefined = iOrganizeService.updateOrganize(orgInsertInfo);
            if (organizeDefined != null) {
                orgSaveVO = new OrgSaveVO();
                BeanUtils.copyProperties(organizeDefined, orgSaveVO);
            }
        }
        return orgSaveVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = {"org_code_defined"}, allEntries = true)
    public OrgSaveVO disableOrg(OrgQueryDTO orgUpdateDTO) {
        // TODO Auto-generated method stub
        OrgSaveVO orgSaveVO = null;
        if (orgUpdateDTO != null && orgUpdateDTO.getId() != null) {
            OrganizeDefined organizeDefined = iOrganizeService.disableOrganize(orgUpdateDTO.getId());
            if (organizeDefined != null) {
                orgSaveVO = new OrgSaveVO();
                BeanUtils.copyProperties(organizeDefined, orgSaveVO);
            }
        }
        return orgSaveVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = {"org_code_defined"}, allEntries = true)
    public OrgSaveVO openOrg(OrgQueryDTO orgUpdateDTO) {
        // TODO Auto-generated method stub
        OrgSaveVO orgSaveVO = null;
        if (orgUpdateDTO != null && orgUpdateDTO.getId() != null) {
            OrganizeDefined organizeDefined = iOrganizeService.openOrganize(orgUpdateDTO.getId());
            if (organizeDefined != null) {
                orgSaveVO = new OrgSaveVO();
                BeanUtils.copyProperties(organizeDefined, orgSaveVO);
            }
        }
        return orgSaveVO;
    }

    @Override
    @Cacheable(value = {"org_code_defined"}, key = "'getOrgNameByOrgCode'+#orgCode", unless = "#result == null")
    public OrgSaveVO getOrgNameByOrgCode(String orgCode) {
        // TODO Auto-generated method stub
        OrgSaveVO orgSaveVO = null;
        if (orgCode != null) {
            int level = SequeceHelper.getLevel(orgCode);
            orgSaveVO = new OrgSaveVO();
            orgSaveVO.setLevel(level);
            int length = 0;
            for (int i = 0; i < level; i++) {
                if (i >= SequeceHelper.SEQUECE_LEVEL_LENGTH.length)
                    length = length + 3;
                else
                    length = length + SequeceHelper.SEQUECE_LEVEL_LENGTH[i];
                OrganizeQuery organizeQuery = new OrganizeQuery();
                organizeQuery.setOrgCode(orgCode.substring(0, length));
                Page<OrganizeDefined> organizeDefineds = iOrganizeService.queryOrganize(organizeQuery);
                if (organizeDefineds != null && organizeDefineds.getContent() != null
                        && organizeDefineds.getContent().size() > 0) {
                    String orgName = orgSaveVO.getName() == null ? organizeDefineds.getContent().get(0).getName()
                            : orgSaveVO.getName() + ">" + organizeDefineds.getContent().get(0).getName();
                    orgSaveVO.setName(orgName);
                }
            }
        }
        return orgSaveVO;
    }

    @Override
    public List<MulOrgsVO> queryMulOrgs(OrgQueryDTO orgQueryDTO) {
        // TODO Auto-generated method stub
        List<MulOrgsVO> mulOrgsVOs = null;
        OrgQueryDTO firOrgDTO = new OrgQueryDTO();
        firOrgDTO.setLevel(1);
        firOrgDTO.setPage(0);
        firOrgDTO.setSize(50);
        firOrgDTO.setOrgCode(orgQueryDTO.getOrgCode());
        firOrgDTO.setStatus(1);
        String keyWords = orgQueryDTO.getKeyWords();
        Page<OrgQueryVO> firOrgVOs = queryOrg(firOrgDTO);
        if (firOrgVOs != null && firOrgVOs.getContent() != null) {
            mulOrgsVOs = firOrgVOs.stream().map(item -> {
                boolean isExistFir = false;
                if (Strings.isNullOrEmpty(keyWords) || item.getNote().contains(keyWords)
                        || item.getName().contains(keyWords))
                    isExistFir = true;
                List<OrgQueryVO> organizeDefineds = null;
                int page = 0;
                while (true) {
                    OrgQueryDTO secOrgQueryDTO = new OrgQueryDTO();
                    secOrgQueryDTO.setLevel(2);
                    secOrgQueryDTO.setParentCode(item.getOrgCode());
                    secOrgQueryDTO.setPage(page);
                    secOrgQueryDTO.setSize(50);
                    secOrgQueryDTO.setStatus(1);
                    if (!isExistFir)
                        secOrgQueryDTO.setKeyWords(keyWords);
                    Page<OrgQueryVO> orgQueryVOs = queryOrg(secOrgQueryDTO);
                    if (orgQueryVOs == null || orgQueryVOs.getContent() == null || orgQueryVOs.getContent().size() == 0)
                        break;
                    if (organizeDefineds == null)
                        organizeDefineds = orgQueryVOs.getContent();
                    else
                        organizeDefineds.addAll(orgQueryVOs.getContent());
                    page++;
                }
                MulOrgsVO mulOrgsVO = null;
                if (organizeDefineds != null || isExistFir) {
                    mulOrgsVO = new MulOrgsVO();
                    mulOrgsVO.setLevelFirOrg(item);
                }
                if (mulOrgsVO != null && mulOrgsVO.getLevelFirOrg() != null) {
                    mulOrgsVO.setLevelSecOrgs(organizeDefineds);
                }
                return mulOrgsVO;
            }).filter(item -> item != null).collect(Collectors.toList());
        }
        return mulOrgsVOs;
    }

    @Override
    public OrgSaveVO checkOrgName(OrgSaveDTO orgSaveDTO) {
        OrgSaveVO orgSaveVO = null;
        if (orgSaveDTO != null) {
            int page = 0;
            while (true) {
                OrganizeQuery organizeQuery = new OrganizeQuery();
                BeanUtils.copyProperties(orgSaveDTO, organizeQuery);
                organizeQuery.setParentCode(orgSaveDTO.getParentOrgCode());
                if (Strings.isNullOrEmpty(orgSaveDTO.getParentOrgCode())) {
                    organizeQuery.setLevel(1);
                } else {
                    organizeQuery.setLevel(SequeceHelper.getLevel(orgSaveDTO.getParentOrgCode()) + 1);
                }
                if (organizeQuery.getLevel() == 3)
                    organizeQuery.setParentCode(null);
                organizeQuery.setPageSize(50);
                organizeQuery.setPage(page);
                organizeQuery.setKeyWords(orgSaveDTO.getName());
                organizeQuery.setStatus(null);
                Page<OrganizeDefined> organizeDefineds = iOrganizeService.queryOrganize(organizeQuery);
                if (organizeDefineds != null && organizeDefineds.getContent() != null
                        && organizeDefineds.getContent().size() > 0) {
                    List<OrgSaveVO> orgSaveVOs = organizeDefineds.stream()
                            .filter(item -> orgSaveDTO.getName().equals(item.getName())).map(item -> {
                                OrgSaveVO temOrgSaveVO = new OrgSaveVO();
                                BeanUtils.copyProperties(item, temOrgSaveVO);
                                return temOrgSaveVO;
                            }).collect(Collectors.toList());
                    if (orgSaveVOs != null && orgSaveVOs.size() > 0) {
                        orgSaveVO = orgSaveVOs.get(0);
                        break;
                    }
                } else {
                    break;
                }
                page++;
            }
        }
        return orgSaveVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = {"org_code_defined"}, allEntries = true)
    public OrgSaveVO updateOrgByCode(OrgUpByCodeDTO orgUpdateDTO) {
        // TODO Auto-generated method stub
        OrgSaveVO orgSaveVO = null;
        if (orgUpdateDTO != null) {
            OrganizeQuery organizeQuery = new OrganizeQuery();
            organizeQuery.setOrgCode(orgUpdateDTO.getOrgCode());
            Page<OrganizeDefined> organizeDefineds = iOrganizeService.queryOrganize(organizeQuery);
            if (organizeDefineds != null && organizeDefineds.getContent() != null
                    && organizeDefineds.getContent().size() > 0) {
                OrganizeDefined organizeDefined = organizeDefineds.getContent().get(0);
                OrganizeUpdateInfo orgInsertInfo = new OrganizeUpdateInfo();
                orgInsertInfo.setId(organizeDefined.getId());
                orgInsertInfo.setName(orgUpdateDTO.getName());
                organizeDefined = iOrganizeService.updateOrganize(orgInsertInfo);
                if (organizeDefined != null) {
                    orgSaveVO = new OrgSaveVO();
                    BeanUtils.copyProperties(organizeDefined, orgSaveVO);
                }
            } else {
                throw new BusiException(BusiEnum.NO_ORGOFCODE);
            }
        }
        return orgSaveVO;
    }

    @Override
    @CacheEvict(value = {"org_code_defined"}, allEntries = true)
    public OrgSaveByExcelVO saveByExcel(OrgSaveMoreDTO orgSaveMoreDTO) {
        OrgSaveByExcelVO orgSaveByExcelVO = null;
        if (orgSaveMoreDTO != null) {
            MultipartFile file = orgSaveMoreDTO.getFile();
            if (file.isEmpty() || file.getOriginalFilename() == null) {
                throw new BusiException(500, "上传文件为空");
            } else if (!file.getOriginalFilename().endsWith(".xls") && !file.getOriginalFilename().endsWith(".xlsx")) {
                throw new BusiException(500, file.getOriginalFilename() + "文件格式不对");
            } else {
                try {
                    orgSaveByExcelVO = new OrgSaveByExcelVO();
                    List<OrgRowSaveVO> orgRowSaveVOs = new ArrayList<OrgRowSaveVO>();
                    InputStream input = new ByteArrayInputStream(file.getBytes());
                    Workbook workbook = WorkbookFactory.create(input);
                    input.close();
                    for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                        Sheet sheet = workbook.getSheetAt(i);
                        Iterator<Row> iteratorRow = sheet.rowIterator();
                        while (iteratorRow.hasNext()) {
                            Row row = iteratorRow.next();
                            OrgSaveDTO orgSaveDTO = new OrgSaveDTO();
                            orgSaveDTO.setParentOrgCode(orgSaveMoreDTO.getParentOrgCode());
                            if (row.getCell(0) != null && !Strings.isNullOrEmpty(getCellValue(row.getCell(0)).trim())) {
                                orgSaveDTO.setName(getCellValue(row.getCell(0)));
                            }
                            if (row.getCell(1) != null && !Strings.isNullOrEmpty(getCellValue(row.getCell(1)).trim())) {
                                orgSaveDTO.setNote(getCellValue(row.getCell(1)));
                            } else {
                                orgSaveDTO.setNote(orgSaveDTO.getName());
                            }
                            OrgRowSaveVO orgRowSaveVO = saveByExcel(orgSaveDTO);
                            if (orgRowSaveVO != null) {
                                if (Strings.isNullOrEmpty(orgRowSaveVO.getErrorMessage())) {
                                    orgSaveByExcelVO.setSuccessNum(orgSaveByExcelVO.getSuccessNum() + 1);
                                } else {
                                    orgSaveByExcelVO.setErrorNum(orgSaveByExcelVO.getErrorNum() + 1);
                                    orgRowSaveVOs.add(orgRowSaveVO);
                                }
                                orgSaveByExcelVO.setCount(orgSaveByExcelVO.getCount() + 1);
                            }
                        }
                        orgSaveByExcelVO.setOrgRowSaveVOs(orgRowSaveVOs);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    throw new BusiException(500, "上传文件失败：" + e.getMessage());
                }
            }
        }
        return orgSaveByExcelVO;
    }

    private OrgRowSaveVO saveByExcel(OrgSaveDTO orgSaveDTO) {
        // TODO Auto-generated method stub
        OrgRowSaveVO orgSaveByExcelVO = null;
        if (orgSaveDTO != null) {
            orgSaveByExcelVO = new OrgRowSaveVO();
            BeanUtils.copyProperties(orgSaveDTO, orgSaveByExcelVO);
            StringBuffer errorMessage = new StringBuffer();
            if (Strings.isNullOrEmpty(orgSaveByExcelVO.getName())) {
                errorMessage.append("组织名称不能为null;");
            }
            if (Strings.isNullOrEmpty(orgSaveByExcelVO.getNote())) {
                errorMessage.append("组织描述不能为null;");
            }
            if (errorMessage.toString().equals("")) {
                try {
                    OrgSaveVO saveOrg = saveOrg(orgSaveDTO);
                    if (saveOrg != null) {
                        BeanUtils.copyProperties(saveOrg, orgSaveByExcelVO, "note");
                    }
                } catch (Exception e) {
                    errorMessage.append(e.getMessage());
                }
            }
            orgSaveByExcelVO.setErrorMessage(errorMessage.toString());
        }
        return orgSaveByExcelVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = {"org_code_defined"}, allEntries = true)
    public OrgSaveVO saveForApp(OrgSaveDTO orgSaveDTO) {
        // TODO Auto-generated method stub
        OrgSaveVO orgSaveVO = null;
        if (orgSaveDTO != null) {
            orgSaveVO = checkOrgName(orgSaveDTO);
            if (orgSaveVO != null && orgSaveVO.getId() != null) {
                if (orgSaveVO.getParentOrgCode().equals(orgSaveDTO.getParentOrgCode())) {
                    return orgSaveVO;
                } else {
                    throw new BusiException(BusiEnum.ORGNAME_REPEATABLE);
                }
            }
            OrgInsertInfo orgInsertInfo = new OrgInsertInfo();
            BeanUtils.copyProperties(orgSaveDTO, orgInsertInfo);
            OrganizeDefined organizeDefined = iOrganizeService.addOrganize(orgInsertInfo);
            if (organizeDefined != null) {
                orgSaveVO = new OrgSaveVO();
                BeanUtils.copyProperties(organizeDefined, orgSaveVO);
            }
        }
        return orgSaveVO;
    }

    @Override
    public List<MulOrgsVO> queryOrgsByMerCode(Set<String> orgCodeList) {
        List<MulOrgsVO> resultOrgsVOList = new ArrayList<>();
        for (String code : orgCodeList) {
            List<MulOrgsVO> mulOrgsVOList = new ArrayList<>();
            int level = SequeceHelper.getLevel(code);
            OrgQueryDTO orgQueryDTO = new OrgQueryDTO();
            orgQueryDTO.setOrgCode(code);
            switch (level) {
                case 0:
                    mulOrgsVOList = queryMulOrgs(orgQueryDTO);
                    break;
                case 1:
                    mulOrgsVOList = queryMulOrgs(orgQueryDTO);
                    break;
                case 2:
                    mulOrgsVOList = queryOrgsLevel2(orgQueryDTO.getOrgCode());
                    break;
                default:
                    OrganizeQuery organizeQuery = new OrganizeQuery();
                    organizeQuery.setOrgCode(orgQueryDTO.getOrgCode());
                    Page<OrganizeDefined> organizeDefineds = iOrganizeService.queryOrganize(organizeQuery);
                    if (organizeDefineds != null && organizeDefineds.getContent() != null
                            && organizeDefineds.getContent().size() > 0) {
                        mulOrgsVOList = queryOrgsLevel2(organizeDefineds.getContent().get(0).getParentOrgCode());
                    }
                    break;
            }
            if (mulOrgsVOList!=null){
                resultOrgsVOList.addAll(mulOrgsVOList);
            }
        }

        return resultOrgsVOList;
    }

    private List<MulOrgsVO> queryOrgsLevel2(String orgCode) {
        List<MulOrgsVO> mulOrgsVOs = null;
        if (!Strings.isNullOrEmpty(orgCode)) {
            OrganizeQuery organizeQuery = new OrganizeQuery();
            organizeQuery.setOrgCode(orgCode);
            Page<OrganizeDefined> organizeDefineds = iOrganizeService.queryOrganize(organizeQuery);
            if (organizeDefineds != null && organizeDefineds.getContent() != null
                    && organizeDefineds.getContent().size() > 0) {
                OrganizeDefined organizeDefined = organizeDefineds.getContent().get(0);
                List<OrgQueryVO> levelSecOrgs = new ArrayList<OrgQueryVO>();
                OrgQueryVO orgQueryVO = new OrgQueryVO();
                BeanUtils.copyProperties(organizeDefined, orgQueryVO);
                levelSecOrgs.add(orgQueryVO);
                organizeQuery = new OrganizeQuery();
                organizeQuery.setOrgCode(organizeDefined.getParentOrgCode());
                organizeDefineds = iOrganizeService.queryOrganize(organizeQuery);
                if (organizeDefineds != null && organizeDefineds.getContent() != null
                        && organizeDefineds.getContent().size() > 0) {
                    organizeDefined = organizeDefineds.getContent().get(0);
                    orgQueryVO = new OrgQueryVO();
                    BeanUtils.copyProperties(organizeDefined, orgQueryVO);
                    mulOrgsVOs = new ArrayList<MulOrgsVO>();
                    MulOrgsVO mulOrgsVO = new MulOrgsVO();
                    mulOrgsVO.setLevelFirOrg(orgQueryVO);
                    mulOrgsVO.setLevelSecOrgs(levelSecOrgs);
                    mulOrgsVOs.add(mulOrgsVO);
                }
            }
        }
        return mulOrgsVOs;
    }

    @Override
    public List<OrgQueryVO> queryAndSaveOrg(List<OrgQueryDTO> orgQueryDTOList) {
        List<OrgQueryVO> result = new ArrayList<>();
        for (OrgQueryDTO queryDTO : orgQueryDTOList) {
            result.add(queryOrGenGenerateOrgCode(queryDTO));
        }
        return result;
    }

    private OrgQueryVO queryOrGenGenerateOrgCode(OrgQueryDTO orgQueryDTO) {
        OrgSaveDTO orgSaveDTO = new OrgSaveDTO();
        orgSaveDTO.setName(orgQueryDTO.getKeyWords());
        orgSaveDTO.setParentOrgCode(orgQueryDTO.getParentCode());

        OrgSaveVO orgSaveVO = checkOrgName(orgSaveDTO);
        OrgQueryVO orgQueryVO = new OrgQueryVO();

        if (orgSaveVO != null && orgSaveVO.getId() != null) {
            if (orgSaveVO.getParentOrgCode().equals(orgSaveDTO.getParentOrgCode())) {
                BeanUtils.copyProperties(orgSaveVO,orgQueryVO);
                return orgQueryVO;
            } else {
                throw new BusiException(BusiEnum.ORGNAME_REPEATABLE);
            }
        }
        //生成code
        OrgInsertInfo orgInsertInfo = new OrgInsertInfo();
        orgInsertInfo.setName(orgQueryDTO.getKeyWords());
        orgInsertInfo.setNote(orgQueryDTO.getNote());
        orgInsertInfo.setParentOrgCode(orgQueryDTO.getParentCode());
        OrganizeDefined organize = iOrganizeService.addOrganize(orgInsertInfo);
        if (organize != null) {
            BeanUtils.copyProperties(organize, orgQueryVO);
            return orgQueryVO;
        }
        throw new BusiException(BusiEnum.GET_ORGCODE_ERROR);
    }

    private static String getCellValue(Cell cell) {
        String strCell = "";
        if (cell == null) {
            return "";
        }
        switch (cell.getCellTypeEnum()) {
            case STRING:
                strCell = cell.getStringCellValue();
                break;
            case NUMERIC:
//            strCell = String.valueOf(cell.getNumericCellValue());
                cell.setCellType(CellType.STRING);
                strCell = cell.getStringCellValue();
                break;
            case BOOLEAN:
                strCell = String.valueOf(cell.getBooleanCellValue());
                break;
            case BLANK:
                strCell = "";
                break;
            default:
                strCell = "";
                break;
        }
        return strCell;
    }

}
