package com.x.organization.assemble.express.jaxrs.identity;

import java.util.ArrayList;
import java.util.List;

import com.x.base.core.project.annotation.FieldDescribe;
import com.x.base.core.project.gson.GsonPropertyObject;
import com.x.base.core.project.jaxrs.StandardJaxrsAction;
import com.x.organization.assemble.express.Business;
import com.x.organization.assemble.express.CacheFactory;
import com.x.organization.core.entity.Identity;
import com.x.organization.core.entity.Person;
import com.x.organization.core.entity.Unit;

import net.sf.ehcache.Ehcache;

class BaseAction extends StandardJaxrsAction {

	Ehcache cache = CacheFactory.getOrganizationCache();

	static class WoIdentityAbstract extends GsonPropertyObject {

		@FieldDescribe("身份识别名")
		private List<String> identityList = new ArrayList<>();

		public List<String> getIdentityList() {
			return identityList;
		}

		public void setIdentityList(List<String> identityList) {
			this.identityList = identityList;
		}

	}

	protected <T extends com.x.base.core.project.organization.Identity> T convert(Business business, Identity identity,
			Class<T> clz) throws Exception {
		T t = clz.newInstance();
		t.setName(identity.getName());
		t.setUnique(identity.getUnique());
		t.setDescription(identity.getDescription());
		t.setDistinguishedName(identity.getDistinguishedName());
		t.setUnitName(identity.getUnitName());
		t.setUnitLevel(identity.getUnitLevel());
		t.setUnitLevelName(identity.getUnitLevelName());
		t.setOrderNumber(identity.getOrderNumber());
		t.setMajor( identity.getMajor() );
		Person p = business.person().pick(identity.getPerson());
		if (null != p) {
			t.setPerson(p.getDistinguishedName());
		}
		Unit u = business.unit().pick(identity.getUnit());
		if (null != u) {
			t.setUnit(u.getDistinguishedName());
		}
		return t;
	}

}