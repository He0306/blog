import request from '@/utils/request'

export function selectTagName(username,password) {
  return request({
    url: '/tag/list',
    method: 'get',
    headers: {
      isToken: false
    },
  })
}
