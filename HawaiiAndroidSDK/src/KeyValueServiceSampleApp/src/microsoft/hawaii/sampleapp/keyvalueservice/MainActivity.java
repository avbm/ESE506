package microsoft.hawaii.sampleapp.keyvalueservice;

import java.util.ArrayList;

import microsoft.hawaii.hawaiiClientLibraryBase.Identities.ClientIdentity;
import microsoft.hawaii.hawaiiClientLibraryBase.Listeners.OnCompleteListener;
import microsoft.hawaii.hawaiiClientLibraryBase.Util.Utility;
import microsoft.hawaii.sdk.keyValueService.KeyValueService;
import microsoft.hawaii.sdk.keyValueService.ServiceContracts.DeleteResult;
import microsoft.hawaii.sdk.keyValueService.ServiceContracts.GetResult;
import microsoft.hawaii.sdk.keyValueService.ServiceContracts.KeyValueItem;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends HawaiiBaseActivity {

	private static final int CREATE_KV_ITEM = 188;

	private ListView resultListView;
	private ProgressBar progressBar;
	private Button addButton;
	private Button deleteButton;
	private Button refreshButton;
	private TextView noRecords;

	private boolean dataDirty = true;
	private ArrayList<String> kvItems;

	private AsyncTask<Void, Integer, AlertDialog.Builder> currentListGetTask;
	private AsyncTask<Void, Integer, AlertDialog.Builder> currentDeleteLoadTask;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		this.addButton = (Button) findViewById(R.id.keyvaluelist_add_button);
		this.deleteButton = (Button) findViewById(R.id.keyvaluelist_delete_button);
		this.refreshButton = (Button) findViewById(R.id.keyvaluelist_refresh_button);
		this.noRecords = (TextView) findViewById(R.id.keyvaluelist_info);

		this.progressBar = (ProgressBar) findViewById(R.id.keyvaluelist_progressbar);
		this.resultListView = (ListView) findViewById(R.id.keyvalue_listview);

		this.addButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				onAddButtonClick(view);
			}
		});

		this.refreshButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				onRefreshButtonClick(v);
			}
		});

		this.deleteButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				onDeleteButtonClick(v);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onStart() {
		super.onStart();

		getKeyValueList(null);
	}

	@Override
	protected void onPause() {
		super.onPause();
		AsyncTask<Void, Integer, AlertDialog.Builder> task = this.currentListGetTask;
		if (task != null) {
			task.cancel(true);
			this.currentListGetTask = null;
		}

		task = this.currentDeleteLoadTask;
		if (task != null) {
			task.cancel(true);
			this.currentDeleteLoadTask = null;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == CREATE_KV_ITEM) {
			if (resultCode == Activity.RESULT_OK && data != null) {
				String key = CreateKeyValueItemActivity.CREATE_OPERATION_RESULT;
				if (data.hasExtra(key) && data.getExtras().getBoolean(key)) {
					this.setDataDirty();
				}
			}
		}
	}

	private void onAddButtonClick(View v) {
		Intent intent = new Intent(this, CreateKeyValueItemActivity.class);
		startActivityForResult(intent, CREATE_KV_ITEM);
	}

	private void onRefreshButtonClick(View v) {
		this.setDataDirty();
		this.getKeyValueList(v);
	}

	private String parseKeyFromCurrentListViewItem() {
		int pos = resultListView.getCheckedItemPosition();
		String itemText = this.resultListView.getItemAtPosition(pos).toString();
		if (!Utility.isStringNullOrEmpty(itemText)) {
			int returnPos = itemText.indexOf("\n");
			if (returnPos > 0) {
				return itemText.substring(0, returnPos);
			}
		}

		return null;
	}

	private void getKeyValueList(final View v) {
		if (!this.isDataDirty()) {
			return;
		}

		final MainActivity thisActivity = this;
		class KeyValueListItemsTask extends
				AsyncTask<Void, Integer, AlertDialog.Builder> {
			private ArrayList<String> listedItems;

			private GetResult serviceResult;

			protected AlertDialog.Builder doInBackground(Void... listTypes) {
				listedItems = new ArrayList<String>();
				OnCompleteListener<GetResult> listener = new OnCompleteListener<GetResult>() {
					public void done(GetResult result) {
						serviceResult = result;
						KeyValueItem[] collection = result
								.getKeyValueCollection();
						for (KeyValueItem item : collection) {
							listedItems.add(String.format("%s\n%s", item
									.getKey()
									.substring(s_commonKeyPrefixLength), item
									.getValue()));
						}
					}
				};

				try {
					ClientIdentity identity = thisActivity.getBaseApplication()
							.getClientIdentity();
					KeyValueService.get(identity, s_CommonKeyPrefix, 10, "",
							listener, null);
				} catch (Exception exception) {
					return dialogToShow("Couldn't execute the list operation",
							exception);
				}

				if (serviceResult.getStatus() != microsoft.hawaii.hawaiiClientLibraryBase.Status.Success) {
					return dialogToShow("Error when getting key-value list",
							serviceResult.getException());
				}

				return null;
			}

			protected void onPreExecute() {
				if (v != null) {
					v.setEnabled(false);
				}

				noRecords.setVisibility(View.GONE);
				resultListView.setVisibility(View.GONE);
				progressBar.setVisibility(View.VISIBLE);
			}

			protected void onPostExecute(AlertDialog.Builder dialogBuilder) {
				if (dialogBuilder != null) {
					thisActivity.showErrorMessage(dialogBuilder);
				} else {
					kvItems = listedItems;
					refreshListView();
				}

				if (v != null) {
					v.setEnabled(true);
				}

				progressBar.setVisibility(View.GONE);
				currentListGetTask = null;
			}
		}

		currentListGetTask = new KeyValueListItemsTask();
		currentListGetTask.execute();
	}

	private void refreshListView() {
		this.setDataClean();
		if (kvItems.isEmpty()) {
			noRecords.setVisibility(View.VISIBLE);
			resultListView.setVisibility(View.GONE);
		} else {
			resultListView.setAdapter(new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_single_choice, kvItems));
			resultListView.setVisibility(View.VISIBLE);
			this.resultListView.setItemChecked(0, true);
		}
	}

	private void onDeleteButtonClick(View v) {
		if (this.resultListView.getCheckedItemPosition() < 0) {
			this.showErrorMessage("select one key value item to delete", null);
		}

		this.deleteButton.setEnabled(false);
		final MainActivity thisActivity = this;
		class KeyValueItemDeleteTask extends
				AsyncTask<Void, Integer, AlertDialog.Builder> {
			private DeleteResult serviceResult;

			protected AlertDialog.Builder doInBackground(Void... listTypes) {
				String[] keys = new String[] { s_CommonKeyPrefix
						+ parseKeyFromCurrentListViewItem() };
				try {
					KeyValueService.deleteByKeys(thisActivity
							.getBaseApplication().getClientIdentity(), keys,
							new OnCompleteListener<DeleteResult>() {
								public void done(DeleteResult result) {
									serviceResult = result;
								}
							}, null);
				} catch (Exception exception) {
					return dialogToShow("Couldn't execute the list operation",
							exception);
				}

				if (serviceResult.getStatus() != microsoft.hawaii.hawaiiClientLibraryBase.Status.Success) {
					return dialogToShow("Error when deleting the item",
							serviceResult.getException());
				} else {
					thisActivity.setDataDirty();
				}

				return null;
			}

			protected void onPreExecute() {
				noRecords.setVisibility(View.GONE);
				resultListView.setVisibility(View.GONE);
				progressBar.setVisibility(View.VISIBLE);
			}

			protected void onPostExecute(AlertDialog.Builder dialogBuilder) {
				if (dialogBuilder != null) {
					thisActivity.showErrorMessage(dialogBuilder);
				} else {
					thisActivity.getKeyValueList(null);
				}

				deleteButton.setEnabled(true);
				progressBar.setVisibility(View.GONE);
				currentDeleteLoadTask = null;
			}
		}

		currentDeleteLoadTask = new KeyValueItemDeleteTask();
		currentDeleteLoadTask.execute();
	}

	private void setDataDirty() {
		this.dataDirty = true;
	}

	private void setDataClean() {
		this.dataDirty = false;
	}

	private boolean isDataDirty() {
		return this.dataDirty;
	}
}
