import React, { useRef } from 'react';
import { Editor } from '@tinymce/tinymce-react';

const TinyMceEditor = (props) => {
  return (
    <>
      <Editor
        id={'tincyEditor'}
        init={{
          language: 'zh_CN',
          plugins:
            'preview searchreplace autolink directionality visualblocks visualchars fullscreen image link template code codesample table charmap hr pagebreak nonbreaking anchor insertdatetime advlist lists wordcount imagetools textpattern help emoticons autosave autoresize formatpainter',
          toolbar:
            'code undo redo restoredraft | cut copy paste pastetext | forecolor backcolor bold italic underline strikethrough link anchor | alignleft aligncenter alignright alignjustify outdent indent | styleselect formatselect fontselect fontsizeselect | bullist numlist | blockquote subscript superscript removeformat | table image media charmap emoticons hr pagebreak insertdatetime print preview | fullscreen | bdmap indent2em lineheight formatpainter axupimgs',
          fontsize_formats: '12px 14px 16px 18px 24px 36px 48px 56px 72px',
          images_upload_handler: (blobInfo, success, failure) => {},
        }}
      />
    </>
  );
};

export default TinyMceEditor;
