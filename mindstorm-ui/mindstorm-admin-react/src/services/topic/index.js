import request from '@/utils/request';

export async function listTopics() {
  return request('/mindstorm-education/education/topic/list');
}

export async function listTopicSelects() {
  return request('/mindstorm-education/education/topic/list/select');
}

export async function createTopic(topicVO) {
  return request('/mindstorm-education/education/topic/create', {
    method: 'POST',
    data: topicVO,
  });
}

export async function infoTopic(topicId) {
  return request(`/mindstorm-education/education/topic/info/${topicId}`);
}

export async function updateTopic(topicVO) {
  return request('/mindstorm-education/education/topic/update', {
    method: 'POST',
    data: topicVO,
  });
}

export async function deleteTopic(topicId) {
  return request(`/mindstorm-education/education/topic/delete/${topicId}`, {
    method: 'DELETE',
  });
}
